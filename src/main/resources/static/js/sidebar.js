// Sidebar toggle logic
document.addEventListener('DOMContentLoaded', function () {
    const sidebar = document.getElementById('sidebar');
    const openSidebarButton = document.getElementById('openSidebarButton');
    const closeSidebarButton = document.getElementById('closeSidebarButton');

    function openNav() {
        sidebar.classList.add('sidebar-open');
        sidebar.classList.remove('sidebar-closed');
    }

    function closeNav() {
        sidebar.classList.add('sidebar-closed');
        sidebar.classList.remove('sidebar-open');
    }

    if (openSidebarButton) {
        openSidebarButton.addEventListener('click', openNav);
    }
    if (closeSidebarButton) {
        closeSidebarButton.addEventListener('click', closeNav);
    }

    // Close sidebar if clicking outside on mobile
    document.addEventListener('click', function (event) {
        const isClickInsideSidebar = sidebar && sidebar.contains(event.target);
        const isClickOnOpenButton = openSidebarButton && openSidebarButton.contains(event.target);
        if (!isClickInsideSidebar && !isClickOnOpenButton && sidebar && sidebar.classList.contains('sidebar-open') && window.innerWidth < 768) {
            closeNav();
        }
    });

    // Initial state on page load
    if (window.innerWidth < 768) {
        closeNav();
    } else {
        openNav();
    }

    // Handle resizing
    window.addEventListener('resize', () => {
        if (window.innerWidth < 768) {
            if (sidebar && sidebar.classList.contains('sidebar-open')) {
                closeNav();
            }
        } else {
            if (sidebar) {
                sidebar.classList.remove('sidebar-closed');
                sidebar.classList.remove('sidebar-open');
            }
        }
    });

    // Highlight active navigation link
    function highlightNavLink(clickedElement) {
        document.querySelectorAll('#sidebar nav a').forEach(link => {
            link.classList.remove('bg-slate-700', 'text-slate-100');
            link.classList.add('text-slate-300', 'hover:bg-slate-700', 'hover:text-slate-100');
        });
        clickedElement.classList.add('bg-slate-700', 'text-slate-100');
        clickedElement.classList.remove('text-slate-300', 'hover:bg-slate-700', 'hover:text-slate-100');
    }

    // Initial highlight
    const currentPath = window.location.pathname;
    document.querySelectorAll('#sidebar nav a').forEach(link => {
        const linkHref = link.getAttribute('th:href') || link.getAttribute('href');
        if (linkHref && currentPath.startsWith(linkHref)) {
            highlightNavLink(link);
        }
    });

    // Listen for HTMX swaps to re-evaluate active link
    document.body.addEventListener('htmx:afterSwap', () => {
        const currentPath = window.location.pathname;
        document.querySelectorAll('#sidebar nav a').forEach(link => {
            const linkHref = link.getAttribute('th:href') || link.getAttribute('href');
            if (linkHref && currentPath.startsWith(linkHref)) {
                highlightNavLink(link);
            }
        });
    });
});