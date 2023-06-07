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
    <el-form
        :size="size"
        :label-position="labelPosition"
        ref="form"
        label-width="auto"
        :model="admin"
    >
      <el-form-item label="姓名">
        <el-input v-model="admin.admin.adminName" :model-value="admin.admin.adminName"></el-input>
      </el-form-item>
      <el-form-item label="昵称">
        <el-input v-model="admin.admin.adminPetName" :model-value="admin.admin.adminPetName"></el-input>
      </el-form-item>
      <el-form-item label="密码">
        <el-input v-model="admin.admin.adminPassword" :model-value="admin.admin.adminPassword"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="sucess" @click="add()">新增</el-button>
        <el-button type="primary" @click="cancelAdd()">取消</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script setup>
import {ref, reactive} from "vue";
import axios from "axios";
import {useRouter} from "vue-router";

const size = ref("default")
const labelPosition = ref("left")
let router = useRouter()
const admin = reactive({
  admin: {
    adminName: '',
    adminPetName: '',
    adminPassword: ''
  }
})

const add=(()=>{
  let addAdmin =JSON.stringify(admin.admin)
  console.log(addAdmin)
  axios.post('api/addAdmin', addAdmin, {headers: {'Content-Type': 'application/json'}}).then(Response => {
    let message = Response.data
    if (confirm(message+" 是否跳转到管理员首页?")) {
      router.push('/admin')
    }
  }).catch(Error=>{
    alert(Error.message+"添加失败,请稍后重试!")
  })
})

const cancelAdd=(()=>{
  router.push('/admin')
})

</script>

<style scoped>
.el-radio-group {
  margin-right: 12px;
}
.el-input{
  width: 30%;
}
.control{
  float: left;
  width: 30%;
}
.el-form{
  margin-left: 10%;
  float: right;
  width: 60%;
}
</style>