// API Base URL - adjust this to match your backend URL
const API_BASE_URL = 'http://localhost:8080/api/todos';

// DOM Elements
let todoForm, titleInput, descriptionInput, todosList, filterSelect, searchInput, searchBtn;

// Function to initialize DOM elements
function initDOMElements() {
    todoForm = document.getElementById('todo-form');
    titleInput = document.getElementById('title');
    descriptionInput = document.getElementById('description');
    todosList = document.getElementById('todos');
    filterSelect = document.getElementById('filter');
    searchInput = document.getElementById('search');
    searchBtn = document.getElementById('search-btn');

    // Check if all elements were found
    if (!todoForm || !titleInput || !descriptionInput || !todosList || !filterSelect || !searchInput || !searchBtn) {
        console.error('Error: Not all DOM elements were found!');
        if (!todoForm) console.error('todo-form element not found');
        if (!titleInput) console.error('title element not found');
        if (!descriptionInput) console.error('description element not found');
        if (!todosList) console.error('todos element not found');
        if (!filterSelect) console.error('filter element not found');
        if (!searchInput) console.error('search element not found');
        if (!searchBtn) console.error('search-btn element not found');
        return false;
    }
    return true;
}

// Event Listeners
document.addEventListener('DOMContentLoaded', function() {
    // Initialize DOM elements
    if (!initDOMElements()) {
        showNotification('Error: Some UI elements were not found. Please check the console for details.', 'error');
        return;
    }

    // Add event listeners
    todoForm.addEventListener('submit', addTodo);
    filterSelect.addEventListener('change', applyFilters);
    searchBtn.addEventListener('click', applyFilters);
    searchInput.addEventListener('keypress', function(e) {
        if (e.key === 'Enter') {
            applyFilters();
        }
    });

    // Load todos
    loadTodos();
});

// Load todos from API
async function loadTodos() {
    try {
        const params = new URLSearchParams();

        // Add filter if not 'all'
        if (filterSelect.value !== 'all') {
            params.append('completed', filterSelect.value === 'completed');
        }

        // Add search term if present
        if (searchInput.value.trim()) {
            params.append('search', searchInput.value.trim());
        }

        const url = `${API_BASE_URL}${params.toString() ? '?' + params.toString() : ''}`;
        const response = await fetch(url);

        if (!response.ok) {
            // Try to get more detailed error message from the response
            const errorData = await response.json().catch(() => null);
            if (errorData && errorData.message) {
                throw new Error(errorData.message);
            } else {
                throw new Error(`Failed to fetch todos (Status: ${response.status})`);
            }
        }

        const todos = await response.json();
        displayTodos(todos);
    } catch (error) {
        console.error('Error loading todos:', error);
        console.error('Error details:', error.message);
        showNotification(`Error loading todos: ${error.message}. Please try again.`, 'error');
    }
}

// Display todos in the UI
function displayTodos(todos) {
    todosList.innerHTML = '';

    if (todos.length === 0) {
        todosList.innerHTML = '<li class="todo-item">No tasks found.</li>';
        return;
    }

    todos.forEach(todo => {
        const todoItem = document.createElement('li');
        todoItem.className = `todo-item ${todo.completed ? 'completed' : ''}`;
        todoItem.innerHTML = `
            <div class="todo-content">
                <div class="todo-title">${todo.title}</div>
                <div class="todo-description">${todo.description || 'No description'}</div>
            </div>
            <div class="todo-actions">
                ${!todo.completed ? 
                    `<button class="btn btn-complete" data-id="${todo.id}">Complete</button>` : 
                    ''}
                <button class="btn btn-delete" data-id="${todo.id}">Delete</button>
            </div>
        `;

        todosList.appendChild(todoItem);
    });

    // Add event listeners to action buttons
    document.querySelectorAll('.btn-complete').forEach(btn => {
        btn.addEventListener('click', completeTodo);
    });

    document.querySelectorAll('.btn-delete').forEach(btn => {
        btn.addEventListener('click', deleteTodo);
    });
}

// Add a new todo
async function addTodo(e) {
    e.preventDefault();

    const title = titleInput.value.trim();
    const description = descriptionInput.value.trim();

    if (!title) {
        showNotification('Title is required', 'error');
        return;
    }

    try {
        const response = await fetch(API_BASE_URL, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ title, description })
        });

        if (!response.ok) {
            // Try to get more detailed error message from the response
            const errorData = await response.json().catch(() => null);
            if (errorData && errorData.message) {
                throw new Error(errorData.message);
            } else {
                throw new Error(`Failed to add todo (Status: ${response.status})`);
            }
        }

        // Reset form
        todoForm.reset();

        // Reload todos
        loadTodos();

        showNotification('Task added successfully', 'success');
    } catch (error) {
        console.error('Error adding todo:', error);
        console.error('Error details:', error.message);
        showNotification(`Error adding task: ${error.message}. Please try again.`, 'error');
    }
}

// Mark a todo as completed
async function completeTodo(e) {
    const todoId = e.target.dataset.id;

    try {
        const response = await fetch(`${API_BASE_URL}/${todoId}/complete`, {
            method: 'PATCH'
        });

        if (!response.ok) {
            throw new Error('Failed to complete todo');
        }

        // Reload todos
        loadTodos();

        showNotification('Task marked as completed', 'success');
    } catch (error) {
        console.error('Error completing todo:', error);
        showNotification('Error completing task. Please try again.', 'error');
    }
}

// Delete a todo
async function deleteTodo(e) {
    const todoId = e.target.dataset.id;

    if (!confirm('Are you sure you want to delete this task?')) {
        return;
    }

    try {
        const response = await fetch(`${API_BASE_URL}/${todoId}`, {
            method: 'DELETE'
        });

        if (!response.ok) {
            throw new Error('Failed to delete todo');
        }

        // Reload todos
        loadTodos();

        showNotification('Task deleted successfully', 'success');
    } catch (error) {
        console.error('Error deleting todo:', error);
        showNotification('Error deleting task. Please try again.', 'error');
    }
}

// Apply filters and search
function applyFilters() {
    loadTodos();
}

// Show notification
function showNotification(message, type = 'info') {
    // Log to console
    if (type === 'error') {
        console.error(message);
    } else if (type === 'warning') {
        console.warn(message);
    } else {
        console.log(message);
    }

    // Show alert
    alert(message);

    // In the future, this could be enhanced with a toast notification library
}
