import{createStore} from "vuex";
export default createStore({
    state:{
        userId:'',
        userPetName:'',
        userPassword:'',

        adminPetName:'',
        adminPassword:'',

        isAdmin:false,
        isUser:false,
    }
})