import {createRouter, createWebHistory} from "vue-router";
import Book from "@/views/book/BookIndex.vue";
import UserIndex from "@/views/user/UserIndex.vue";
import AdminIndex from "@/views/admin/AdminIndex.vue";
import Login from "@/views/Login.vue";
import store from "@/store";
import BookInformation from "@/views/book/BookInformation.vue";
import AlterAdmin from "@/views/admin/AlterAdmin.vue";
import AddAdmin from "@/views/admin/AddAdmin.vue";
import AlterUser from "@/views/admin/AlterUser.vue";
import AddUser from "@/views/admin/AddUser.vue";
import AdminUsers from "@/views/admin/AdminUsers.vue";
import AdminBooks from "@/views/admin/AdminBooks.vue";
import AlterBooks from "@/views/admin/AlterBooks.vue";
import AddBook from "@/views/admin/AddBook.vue";
import AddBookSeries from "@/views/admin/AddBookSeries.vue";
import AlterBookSeries from "@/views/admin/AlterBookSeries.vue";
import AdminBookSeries from "@/views/admin/AdminBookSeries.vue";
import AdminBag from "@/views/admin/AdminBag.vue";
import UserBag from "@/views/user/UserBag.vue";

export default createRouter({
    history: createWebHistory(),
    routes: [
        {
            name: 'book',
            path: '/book',
            alias: '/',
            component: Book
        },
        {
            name: 'user',
            path: '/user',
            component: UserIndex,
            beforeEnter: (to, from, next) => {
                if (to.name === 'user') {
                    let password = store.state.userPassword
                    let name = store.state.userPetName
                    let user = {
                        userName: name,
                        userPassword: password
                    }
                    if (password && name) {
                        sessionStorage.setItem('user', user)
                        // 一个时间只能登录一个身份时开启下面的
                        // sessionStorage.removeItem('admin')
                        next()
                    } else {
                        next(false)
                    }
                } else next({path: '/login'})
            }
        },
        {
            name: 'admin',
            path: '/admin',
            component: AdminIndex,
            beforeEnter: (to, from, next) => {
                if (to.name === 'admin') {
                    let password = store.state.adminPassword
                    let name = store.state.adminPetName
                    let admin = {
                        adminName: name,
                        adminPassword: password
                    }
                    // 为了方便代码编写先将判断关闭
                    // if (password && name) {
                        if(1){
                        sessionStorage.setItem('admin', admin)

                        // 一个时间只能登录一个身份时开启下面的语句
                        // sessionStorage.removeItem('user')
                        next()
                    } else {
                        next(false)
                    }
                } else next({path: '/login'})
            }
        },
        {
            name: 'login',
            path: '/login',
            component: Login,
            meta: {
                keepAlive: false
            }
        },
        {
            name: 'information',
            path: '/information',
            component: BookInformation,
            props({query}) {
                return {
                    id: query.id
                }
            }
        },
        {
            name: 'alterAdmin',
            path: '/alterAdmin',
            component: AlterAdmin,
            props({query}) {
                return {
                    adminId: query.adminId
                }
            },
            beforeEnter: (to, from, next) => {
                if (to.name === 'alterAdmin') {
                    if (store.state.isAdmin) {
                        next()
                    } else {
                        next(false)
                    }
                }
            }
        },
        {
            name: 'addAdmin',
            path: '/addAdmin',
            component: AddAdmin,
            beforeEnter: (to, from, next) => {
                if (to.name === 'addAdmin') {
                    if (store.state.isAdmin) {
                        next()
                    } else {
                        next(false)
                    }
                }
            }
        },
        {
            name: 'adminUsers',
            path: '/adminUsers',
            component: AdminUsers,
            beforeEnter: (to, from, next) => {
                if (to.name === 'adminUsers') {
                    if (store.state.isAdmin) {
                        next()
                    } else {
                        next(false)
                    }
                }
            }
        },
        {
            name: 'alterUser',
            path: '/alterUser',
            component: AlterUser,
            props({query}) {
                return {
                    userId: query.userId
                }
            },
            beforeEnter: (to, from, next) => {
                if (to.name === 'alterUser') {
                    if (store.state.isAdmin || store.state.isUser) {
                        next()
                    } else {
                        next(false)
                    }
                }
            }
        },
        {
            name: 'addUser',
            path: '/addUser',
            component: AddUser,
        },
        {
            name: 'adminBooks',
            path: '/adminBooks',
            component: AdminBooks,
            beforeEnter: (to, from, next) => {
                if (to.name === 'adminBooks') {
                    if (store.state.isAdmin) {
                        next()
                    } else {
                        next(false)
                    }
                }
            }
        },
        {
            name: 'alterBooks',
            path: '/alterBooks',
            component: AlterBooks,
            props({query}) {
                return {
                    bookId: query.bookId
                }
            },
            beforeEnter: (to, from, next) => {
                if (to.name === 'alterBooks') {
                    if (store.state.isAdmin) {
                        next()
                    } else {
                        next(false)
                    }
                }
            }
        },
        {
            name: 'addBook',
            path: '/addBook',
            component: AddBook,
            beforeEnter: (to, from, next) => {
                if (to.name === 'addBook') {
                    if (store.state.isAdmin) {
                        next()
                    } else {
                        next(false)
                    }
                }
            }
        },
		
		
		
		{
		    name: 'adminBookSeries',
		    path: '/adminBookSeries',
		    component: AdminBookSeries,
		    beforeEnter: (to, from, next) => {
		        if (to.name === 'adminBookSeries') {
		            if (store.state.isAdmin) {
		                next()
		            } else {
		                next(false)
		            }
		        }
		    }
		},
		{
		    name: 'alterBookSeries',
		    path: '/alterBookSeries',
		    component: AlterBookSeries,
		    props({query}) {
		        return {
		            seriesId: query.seriesId
		        }
		    },
		    beforeEnter: (to, from, next) => {
		        if (to.name === 'alterBookSeries') {
		            if (store.state.isAdmin) {
		                next()
		            } else {
		                next(false)
		            }
		        }
		    }
		},
		{
		    name: 'addBookSeries',
		    path: '/addBookSeries',
		    component: AddBookSeries,
		    beforeEnter: (to, from, next) => {
		        if (to.name === 'addBookSeries') {
		            if (store.state.isAdmin) {
		                next()
		            } else {
		                next(false)
		            }
		        }
		    }
		},
		{
		    name: 'adminBag',
		    path: '/adminBag',
		    component: AdminBag,
		    beforeEnter: (to, from, next) => {
		        if (to.name === 'adminBag') {
		            if (store.state.isAdmin) {
		                next()
		            } else {
		                next(false)
		            }
		        }
		    }
		},
		{
		    name: 'userBag',
		    path: '/userBag',
		    component: UserBag,
		    beforeEnter: (to, from, next) => {
		        if (to.name === 'userBag') {
		            if (store.state.isUser) {
		                next()
		            } else {
		                next(false)
		            }
		        }
		    }
		},
    ]
})