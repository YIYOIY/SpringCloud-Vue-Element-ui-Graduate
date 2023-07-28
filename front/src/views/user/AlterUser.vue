<template>
  <div style="margin: 10% 2%; text-align: center; align-content: center">
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
    <br />
    <el-form :size="size" :label-position="labelPosition" ref="form" label-width="auto" :model="user.user" :rules="rule">
      <el-form-item label="姓名" prop="userName">
        <el-input type="text" v-model="user.user.userName" disabled></el-input>
      </el-form-item>
      <el-form-item label="性别" prop="userSex">
        <el-radio-group v-model="user.user.userSex">
          <el-radio label="男">男</el-radio>
          <el-radio label="女">女</el-radio>
          <el-radio label="保密">保密</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="生日" clearable prop="userBirth">
        <el-date-picker clearable v-model="user.user.userBirth" :model-value="user.user.userBirth" align="right"
          type="date" format="YYYY 年 MM 月 DD 日">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="密码" prop="userPassword">
        <el-input v-model="user.user.userPassword" show-password :model-value="user.user.userPassword"></el-input>
      </el-form-item>
      <el-form-item label="电话" prop="userPhone">
        <el-input v-model="user.user.userPhone" :model-value="user.user.userPhone"></el-input>
      </el-form-item>
      <el-form-item label="地址" prop="userAddress">
        <el-input v-model="user.user.userAddress"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="danger" @click="alter()">更新</el-button>
        <el-button type="sucess" @click="back()">返回</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script setup>
import { useRouter } from "vue-router";
import { ref, reactive, onMounted, toRef } from "vue";
import { ElNotification } from "element-plus";
import {alterUser, alterUserGet} from "@/api/UserApi";
let router = useRouter();
const prop = defineProps(["userId"]);
let id = toRef(prop, "userId");
const size = ref("default");
const labelPosition = ref("left");
let rule = reactive({
  userAddress: [
    {
      required: false,
      message: "Please select Activity zone",
      trigger: "blur",
    },
    { min: 1, max: 100, message: "长度在 1 到 100 个字符", trigger: "blur" },
  ],
  userPassword: [
    { required: false, message: "请输入密码", trigger: "blur" },
    { min: 6, max: 25, message: "长度在 6 到 25 个字符", trigger: "blur" },
  ],
});

const user = reactive({
  user: {
    userName: "",
    userSex: "",
    userPassword: "",
    userBirth: "",
    userPhone: "",
    userAddress: "",
  },
});

onMounted(async () => {
  await alterUserGet(id.value).then((Response) => {
    user.user = Response.data;
  });
});

let alter = () => {
  let User = JSON.stringify(user.user);
  console.log(User);
  alterUser(User).then((Response) => {
      if (confirm(Response.message + "!  是否跳转到用户页")) {
        router.push({
          name: "adminUsers",
        });
      }
    }).catch((Error) => {
      ElNotification({
        message: Error.data.message + "  请重新输入!",
        title: '错误',
        type: 'error',
        Position: 'top-right'
      })
    });
};

let back = () => {
  router.push({
    name: "adminUsers",
  });
};
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
