

$(function(){
    hideQuiz();
    addListeners();
});

function addListeners(){

    document.getElementById("waypointForm:radioInteractive:0").addEventListener("click",hideQuiz);
    document.getElementById("waypointForm:radioInteractive:1").addEventListener("click",showQuiz);


}

function hideQuiz(){
    document.getElementById("waypointForm:quizInput").style.display = 'none';
}

function showQuiz(){
    document.getElementById("waypointForm:quizInput").style.display = 'block';
}
