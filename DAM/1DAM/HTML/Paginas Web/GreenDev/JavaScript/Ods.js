function toggleExplanation(id) {
    var explanation = document.getElementById(id);
    if (explanation.style.display === 'block') {
        explanation.style.display = 'none';
    } else {
        var explanations = document.querySelectorAll('.explanation');
        explanations.forEach(function (explanation) {
            explanation.style.display = 'none';
        });
        explanation.style.display = 'block';
    }
}