// handling movie average display
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
document.getElementById(ratingString)?.classList.add("active");


// handling user star rating
const ratingLabels= [...document.getElementsByClassName("ratingControl-stars")];
const ratingInputs=[...document.getElementsByClassName("ratingControl-input")];

ratingLabels.map((label, i) => {
    label.addEventListener("click", () => {
        unCheckAllStars();
        checkStar(ratingInputs[i]);
    })
})

const checkStar = (input) => {
    input.checked = true;
}

const unCheckAllStars = () => {
   ratingInputs.map(input => {
        input.checked = false;
    })
}


// handle add to favorite
const base = "http://localhost:8080/";

const headers = {
    'Content-Type':'application/json'
}

const saveToFav = document.getElementById("saveToFav");

saveToFav.addEventListener("click", () => {
   saveToFavorite();
})

async function saveToFavorite() {
    const obj = {
        id : saveToFav.dataset.viewid ? saveToFav.dataset.viewid : null,
        userId : saveToFav.dataset.userid,
        movieId : saveToFav.dataset.movieid 
    }
    const response = await fetch(`${base}api/addToFavorite`, {
        method: "POST",
        body: JSON.stringify(obj), 
        headers: headers
    })
    .catch(error => console.error(error.message))
    console.log(response);
    if(response.status == 200) {
        window.location.href=`${base}movies/getMovieById?movieId=${saveToFav.dataset.movieid}`
    }
}

// handle reviews by other users

const reviewStarWrappers = document.getElementsByClassName("movie_review_stars_wrapper");
window.onload = function() {
    [...reviewStarWrappers].map(wrapper => {
        const viewId = wrapper.dataset.viewid;
        const userRating = wrapper.dataset.userrating * 10;
        document.getElementById(viewId + "_rating-" + userRating).classList.add("active");
    })
}
