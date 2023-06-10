import{createStore} from "vuex";
export default createStore({
    state:{
        userId:'',
        userName:'',
        userPassword:'',

        adminName:'',
        adminPassword:'',

        isAdmin:false,
        isUser:false,
    }
})