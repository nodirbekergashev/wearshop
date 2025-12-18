// ============================================
// AUTHENTICATION PAGES SCRIPTS
// Power of Wear - Auth Module
// ============================================

document.addEventListener('DOMContentLoaded', function() {
    // Initialize all authentication page functionality
    initAuthPages();
});

function initAuthPages() {
    initPasswordToggle();
    initPasswordStrength();
    initRememberMe();
    initFormValidation();
    initSocialButtons();
    initLoadingStates();
}

// Toggle password visibility
function initPasswordToggle() {
    const toggleButtons = document.querySelectorAll('.toggle-password');

    toggleButtons.forEach(button => {
        button.addEventListener('click', function() {
            const targetId = this.getAttribute('data-target');
            const passwordInput = document.getElementById(targetId);
            const icon = this.querySelector('i');

            if (passwordInput.type === 'password') {
                passwordInput.type = 'text';
                icon.className = 'fas fa-eye-slash';
                this.setAttribute('aria-label', 'Hide password');
            } else {
                passwordInput.type = 'password';
                icon.className = 'fas fa-eye';
                this.setAttribute('aria-label', 'Show password');
            }
        });
    });
}

// Password strength indicator
function initPasswordStrength() {
    const passwordInput = document.getElementById('password');
    if (!passwordInput) return;

    passwordInput.addEventListener('input', function(e) {
        const password = e.target.value;
        updatePasswordStrength(password);
    });

    // Check on page load if there's already a value
    if (passwordInput.value) {
        updatePasswordStrength(passwordInput.value);
    }
}

function updatePasswordStrength(password) {
    const strengthBar = document.getElementById('password-strength-bar');
    const strengthText = document.getElementById('password-strength-text');

    if (!strengthBar || !strengthText) return;

    let strength = 0;
    let strengthClass = '';
    let strengthMessage = '';

    // Calculate strength
    if (password.length >= 8) strength++;
    if (/[A-Z]/.test(password)) strength++;
    if (/[0-9]/.test(password)) strength++;
    if (/[^A-Za-z0-9]/.test(password)) strength++;

    // Determine strength level
    switch(strength) {
        case 0:
            strengthClass = 'strength-weak';
            strengthMessage = 'Very Weak';
            break;
        case 1:
            strengthClass = 'strength-weak';
            strengthMessage = 'Weak';
            break;
        case 2:
            strengthClass = 'strength-fair';
            strengthMessage = 'Fair';
            break;
        case 3:
            strengthClass = 'strength-good';
            strengthMessage = 'Good';
            break;
        case 4:
            strengthClass = 'strength-strong';
            strengthMessage = 'Strong';
            break;
    }

    // Update UI
    strengthBar.className = `password-strength-bar ${strengthClass}`;
    strengthText.textContent = strengthMessage;
}

// Remember me functionality
function initRememberMe() {
    const rememberMe = document.getElementById('rememberMe');
    const emailInput = document.getElementById('email');

    if (!rememberMe || !emailInput) return;

    // Load remembered email
    const rememberedEmail = localStorage.getItem('rememberedEmail');
    if (rememberedEmail) {
        emailInput.value = rememberedEmail;
        rememberMe.checked = true;
    }

    // Save email when checkbox is checked
    rememberMe.addEventListener('change', function() {
        if (this.checked && emailInput.value) {
            localStorage.setItem('rememberedEmail', emailInput.value);
        } else {
            localStorage.removeItem('rememberedEmail');
        }
    });

    // Auto-check when email is entered
    emailInput.addEventListener('input', function() {
        if (this.value && localStorage.getItem('rememberedEmail') === this.value) {
            rememberMe.checked = true;
        }
    });
}

// Form validation
function initFormValidation() {
    const forms = document.querySelectorAll('.auth-form');

    forms.forEach(form => {
        // Real-time validation
        const inputs = form.querySelectorAll('input[required]');
        inputs.forEach(input => {
            input.addEventListener('blur', function() {
                validateField(this);
            });
        });

        // Form submission validation
        form.addEventListener('submit', function(e) {
            if (!validateForm(this)) {
                e.preventDefault();
                showFormErrors(this);
            }
        });
    });
}

function validateField(field) {
    const value = field.value.trim();
    const errorElement = field.parentElement.querySelector('.error-message');

    if (!value && field.required) {
        showError(field, 'This field is required');
        return false;
    }

    // Email validation
    if (field.type === 'email' && value) {
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if (!emailRegex.test(value)) {
            showError(field, 'Please enter a valid email address');
            return false;
        }
    }

    // Password confirmation
    if (field.id === 'confirmPassword') {
        const password = document.getElementById('password')?.value;
        if (password && value !== password) {
            showError(field, 'Passwords do not match');
            return false;
        }
    }

    // Clear error if valid
    clearError(field);
    return true;
}

function validateForm(form) {
    let isValid = true;
    const inputs = form.querySelectorAll('input[required]');

    inputs.forEach(input => {
        if (!validateField(input)) {
            isValid = false;
        }
    });

    return isValid;
}

function showError(field, message) {
    clearError(field);

    const errorDiv = document.createElement('div');
    errorDiv.className = 'error-message';
    errorDiv.innerHTML = `<i class="fas fa-exclamation-circle"></i> ${message}`;

    field.parentElement.appendChild(errorDiv);
    field.classList.add('is-invalid');
}

function clearError(field) {
    const errorDiv = field.parentElement.querySelector('.error-message');
    if (errorDiv) {
        errorDiv.remove();
    }
    field.classList.remove('is-invalid');
}

function showFormErrors(form) {
    const firstInvalid = form.querySelector('.is-invalid');
    if (firstInvalid) {
        firstInvalid.focus();
    }
}

// Social login buttons (placeholder)
function initSocialButtons() {
    const socialButtons = document.querySelectorAll('.btn-social');

    socialButtons.forEach(button => {
        button.addEventListener('click', function() {
            const provider = this.querySelector('i').className.includes('google') ? 'Google' : 'Facebook';
            alert(`${provider} login will be implemented soon!`);
        });
    });
}

// Loading states
function initLoadingStates() {
    const forms = document.querySelectorAll('.auth-form');

    forms.forEach(form => {
        form.addEventListener('submit', function() {
            const submitBtn = this.querySelector('.btn-auth');
            const loadingDiv = document.getElementById('auth-loading');

            if (submitBtn && loadingDiv) {
                submitBtn.disabled = true;
                submitBtn.innerHTML = '<span class="spinner-border spinner-border-sm me-2"></span> Processing...';
                loadingDiv.classList.add('active');
            }
        });
    });
}

// Utility functions
function debounce(func, wait) {
    let timeout;
    return function executedFunction(...args) {
        const later = () => {
            clearTimeout(timeout);
            func(...args);
        };
        clearTimeout(timeout);
        timeout = setTimeout(later, wait);
    };
}

// Export functions for use in other scripts
window.AuthUtils = {
    validatePassword: function(password) {
        const minLength = 8;
        const hasUpperCase = /[A-Z]/.test(password);
        const hasLowerCase = /[a-z]/.test(password);
        const hasNumbers = /\d/.test(password);
        const hasSpecialChar = /[^A-Za-z0-9]/.test(password);

        return {
            isValid: password.length >= minLength && hasUpperCase && hasLowerCase && hasNumbers,
            details: {
                length: password.length >= minLength,
                upperCase: hasUpperCase,
                lowerCase: hasLowerCase,
                numbers: hasNumbers,
                specialChar: hasSpecialChar
            }
        };
    },

    formatPhoneNumber: function(phone) {
        // Simple phone formatting
        return phone.replace(/(\d{3})(\d{3})(\d{4})/, '($1) $2-$3');
    }
};