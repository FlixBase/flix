const reviewStarWrappers = document.getElementsByClassName("movie_stars_wrapper");

[...reviewStarWrappers].map(wrapper => {
    const viewId = wrapper.dataset.viewid;
    const userRating = wrapper.dataset.userrating * 10;
    document.getElementById(viewId + "_rating-" + userRating).classList.add("active");
})