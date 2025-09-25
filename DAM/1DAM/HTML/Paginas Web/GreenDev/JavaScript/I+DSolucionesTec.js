function toggleVisibility(selectedItem) {
    const items = document.querySelectorAll('#solutionList li');
    const isExpanded = selectedItem.classList.contains('expanded');
    items.forEach(item => {
        item.classList.remove('expanded');
    });
    if (!isExpanded) {
        selectedItem.classList.add('expanded');
    }
}