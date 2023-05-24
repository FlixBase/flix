const movieAvgRating = document.getElementById("movieAvgRating").dataset.averagerating / 2;

const getStarRating = (initialAvgRating) => {
    let remainder = movieAvgRating % 1;
    let rating = movieAvgRating - remainder;
    if(remainder >= 0.75) return rating + 1;
    if(remainder >= 0.25) return rating + 0.5;
    return rating;
}

const movieStarRating = getStarRating(movieAvgRating);
const ratingString = "rating-" + movieStarRating*10;
document.getElementById(ratingString).classList.add("active");
console.log(ratingString);