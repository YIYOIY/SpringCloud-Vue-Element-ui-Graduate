import http from "@/utils/http";

export function getBooks() {
    return http.get(
        'api/book'
    )
}

export function getBooksByName(params) {
    return http.get(
        'api/book?searchName='+params
    )
}
export function getBooksById(params) {
    return http.get(
        'api/lookup?bookId='+params
    )
}

export function getSeries() {
    return http.get(
        'api/series'
    )
}

export function getSelectBySeries(params) {
    return http.get(
        `api/selectBySeries?seriesName=`+params
    )
}

export function alterBook(params) {
    return http.put(
        'api/book',
        params
    )
}

export function deleteBook(params) {
    return http.delete(
        `api/book?bookId=`+params
    )
}
export function addBook(data) {
    return http.post(
        'api/book',
        data
    )
}
