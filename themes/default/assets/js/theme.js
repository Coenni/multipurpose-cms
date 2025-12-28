// Default theme JavaScript
(function() {
  'use strict';
  
  // Mobile menu toggle
  function initMobileMenu() {
    const menuToggle = document.querySelector('.menu-toggle');
    const navigation = document.querySelector('.theme-nav');
    
    if (menuToggle && navigation) {
      menuToggle.addEventListener('click', function() {
        navigation.classList.toggle('active');
      });
    }
  }
  
  // Smooth scroll for anchor links
  function initSmoothScroll() {
    document.querySelectorAll('a[href^="#"]').forEach(anchor => {
      anchor.addEventListener('click', function (e) {
        const target = document.querySelector(this.getAttribute('href'));
        if (target) {
          e.preventDefault();
          target.scrollIntoView({
            behavior: 'smooth',
            block: 'start'
          });
        }
      });
    });
  }
  
  // Initialize on DOM ready
  document.addEventListener('DOMContentLoaded', function() {
    initMobileMenu();
    initSmoothScroll();
  });
})();
