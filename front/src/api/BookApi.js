import http from "@/utils/http";

export function getBooks(searchName, pageNo, pageSize) {
    return http.get(
        `api/book/${searchName}/${pageNo}/${pageSize}`
    )
}

export function shopkeeperGetBooks(searchName, pageNo, pageSize,shopkeeperId) {
    return http.get(
        `api/book/${searchName}/${pageNo}/${pageSize}/${shopkeeperId}`
    )
}

export function selectBySeries(seriesName, pageNo, pageSize) {
    return http.get(
        `api/select_by_series/${seriesName}/${pageNo}/${pageSize}`
    )
}

export function getBooksById(bookId) {
    return http.get(
        `api/lookup/${bookId}`
    )
}

export function addBook(data) {
    return http.post(
        'api/book',
        data
    )
}

export function deleteBook(data) {
    return http.delete(
        `api/book`,
        data
    )
}

export function alterBook(data) {
    return http.put(
        'api/book',
        data
    )
}




