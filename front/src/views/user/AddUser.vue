<template>
  <div style="margin: 10% 2%;text-align: center;align-content: center">
    <div class="control">
      <p style="margin-left: 5%">size control</p>
      <el-radio-group v-model="size">
        <el-radio label="large">large</el-radio>
        <el-radio label="default">default</el-radio>
        <el-radio label="small">small</el-radio>
      </el-radio-group>
      <p style="margin-left: 5%">Position Control</p>
      <el-radio-group v-model="labelPosition">
        <el-radio label="left">left</el-radio>
        <el-radio label="right">right</el-radio>
        <el-radio label="top">top</el-radio>
      </el-radio-group>
    </div>
    <el-form :size="size" :label-position="labelPosition" ref="form" label-width="auto" :model="user.user" :rules="rule">
      <el-form-item label="姓名" clearable prop="userName">
        <el-input v-model="user.user.userName"></el-input>
      </el-form-item>
      <el-form-item label="性别">
        <el-select v-model="user.user.userSex" filterable clearable placeholder="选择性别">
          <el-option label="男" value="男" />
          <el-option label="女" value="女" />
          <el-option label="保密" value="保密" />
        </el-select>
      </el-form-item>
      <el-form-item label="生日" clearable>
        <el-date-picker clearable v-model="user.user.userBirth" :model-value="user.user.userBirth" align="right"
          type="date" format="YYYY 年 MM 月 DD 日">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="密码" prop="userPassword" clearable>
        <el-input type="password" show-password v-model="user.user.userPassword"></el-input>
      </el-form-item>
      <el-form-item label="电话">
        <el-input v-model="user.user.userPhone" :model-value="user.user.userPhone"></el-input>
      </el-form-item>

      <el-form-item label="地址" prop="userAddress">
        <el-input v-model="user.user.userAddress"></el-input>
      </el-form-item>

      <el-form-item>
        <el-button type="sucess" @click="add()">新增</el-button>
        <el-button type="primary" @click="cancelAdd()">取消</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script setup>
import { ref, reactive } from "vue";
import axios from "axios";
import { useRouter } from "vue-router";
import { useRoute } from "vue-router";
const size = ref("default")
const labelPosition = ref("left")
let router = useRouter()
let route = useRoute()

let isEnroll = ref(false)
if (route.query.enroll) {
  isEnroll.value = true
}
console.log(isEnroll.value)

const user = reactive({
  user: {
    userName: '',
    userSex: '保密',
    userPassword: '',
    userBirth: '',
    userPhone: '',
    userAddress: '',
  }
})

let rule = reactive({
  userName: [
    { required: true, message: "请输入姓名", trigger: "blur" },
    { min: 1, max: 20, message: "长度在 1 到 20 个字符", trigger: "blur" },
  ],
  userAddress: [
    {
      required: false,
      message: "Please select Activity zone",
      trigger: "blur",
    },
    { min: 1, max: 100, message: "长度在 1 到 100 个字符", trigger: "blur" },
  ],
  userPassword: [
    { required: true, message: "请输入密码", trigger: "blur" },
    { min: 6, max: 25, message: "长度在 6 到 25 个字符", trigger: "blur" },
  ],
});

const add = (() => {
  let addUser = JSON.stringify(user.user)
  console.log(addUser)
  axios.post('api/addUser', addUser, { headers: { 'Content-Type': 'application/json' } }).then(Response => {
    let message = Response.data
    if (isEnroll.value) {
      alert("注册成功!")
      router.push('/login')
    } else {
      if (confirm(message + "是否跳转到用户首页?")) {
        router.push('/adminUsers')
      }
    }
  }).catch(Error => {
    alert(Error.message + "添加失败,请稍后重试!")
  })
})

const cancelAdd = (() => {
  router.push('/adminUsers')
})

</script>

<style scoped>
.el-radio-group {
  margin-right: 12px;
}

.el-input {
  width: 30%;
}

.control {
  float: left;
  width: 30%;
}

.el-form {
  margin-left: 10%;
  float: right;
  width: 60%;
}
</style>