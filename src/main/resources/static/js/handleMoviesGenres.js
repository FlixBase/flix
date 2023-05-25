const pathStr = window.location.search;
const urlParamsSearch = new URLSearchParams(pathStr);
let btn;
if(urlParamsSearch.has('genreId')) {
    const genreId = urlParamsSearch.get("genreId");
    btn = document.getElementById("genre_" + genreId);
    btn.classList.remove("btn-light");
    btn.classList.add("btn-info");
} else {
    btn = document.getElementById("all_genres");
    btn.classList.remove("btn-light");
    btn.classList.add("btn-info");
}

