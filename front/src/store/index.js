import {createStore} from "vuex";

export default createStore({
    state: {
        // userId和adminId用于删除时验证是否是自身
        userId: '',
        userName: '',
        userPassword: '',

        adminId:'',
        adminName: '',
        adminPassword: '',

        isAdmin: false,
        isUser: false,

        menu:1,
    },
    mutations:{
        clearState(state){
            state.adminPassword = ''
            state.adminName = ''
            state.adminId = ''
            state.userPassword = ''
            state.userName = ''
            state.userId = ''
            state.isAdmin = false
            state.isUser = false
            sessionStorage.removeItem('token')
            sessionStorage.removeItem('store')
        }
    }
})