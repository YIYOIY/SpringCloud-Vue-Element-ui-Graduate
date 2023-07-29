<template>
  <div id="building">
    <el-form label-position="top" ref="form" label-width="100" :model="user.user" :rules="rule" style="width: 100%">
      <div style="left: 10%;max-width: 15%;position: absolute">
        <el-form-item label="账户名称"  prop="userName">
          <el-input v-model="user.user.userName" clearable placeholder="在此输入账户名称" disabled></el-input>
        </el-form-item>

        <el-form-item label="性别">
          <el-select v-model="user.user.userSex" filterable clearable placeholder="选择性别">
            <el-option label="男" value="男"/>
            <el-option label="女" value="女"/>
            <el-option label="保密" value="保密"/>
          </el-select>
        </el-form-item>

        <el-form-item label="出生日期">
          <el-date-picker clearable v-model="user.user.userBirth"
                          type="date" format="YYYY 年 MM 月 DD 日"  placeholder="在此选择出生日期">
          </el-date-picker>
        </el-form-item>
      </div>


      <div style="right: 50%;max-width: 25%;position: absolute">

        <el-form-item label="账户密码" prop="userPassword">
          <el-input type="password" show-password v-model="user.user.userPassword" clearable  placeholder="在此输入账户密码"></el-input>
        </el-form-item>

        <el-form-item label="联系方式">
          <el-input v-model="user.user.userPhone"  clearable  placeholder="在此输入联系方式"></el-input>
        </el-form-item>
      </div>

      <div style="right: 10%;width: 25%;position: absolute">
        <el-form-item label="地址" prop="userAddress" @change="next">
          <el-input type="textarea" :autosize="{ minRows: 5, maxRows: 10 }" v-model="user.user.userAddress"
                    clearable placeholder="在此输入收货地址"></el-input>
        </el-form-item>
      </div>
    </el-form>

    <div style="right: 30%;top:50%;width:50%;position: absolute">
      <el-row :gutter="40" class="control">
        <el-col :span="12">
          <el-button type="danger" size="large" @click="alter()" plain round>更新</el-button>
        </el-col>
        <el-col :span="12">
          <el-button type="success"  size="large" @click="back()"  plain round>返回</el-button>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script setup>
import { useRouter } from "vue-router";
import {reactive, onMounted, toRef, ref, nextTick} from "vue";
import {ElLoading, ElMessage, ElNotification} from "element-plus";
import {alterUser, alterUserGet} from "@/api/UserApi";
let router = useRouter();
const prop = defineProps(["userId"]);
let id = toRef(prop, "userId");
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

const form = ref(null)
let alter = (() => {
  form.value.validate(valid => {
    if (!valid) {
      ElMessage.error("请输入信息符合要求!");
      return false;
    }

    let User = JSON.stringify(user.user);
    console.log(User);

    alterUser(User).then((Response) => {
      ElMessage.success(Response.message )
        router.push({
          name: "adminUsers",
        });
    }).catch((Error) => {
      ElNotification({
        message: Error.data.message + "  请重新输入!",
        title: '错误',
        type: 'error',
        Position: 'top-right'
      })
    });
  });
})

let back = () => {
  router.push({
    name: "adminUsers",
  });
};
</script>

<style scoped>
#building {
  background: url(../../assets/static/alterUser.jpg);
  width: 100%;
  height: 100%;
  font-size: large;
  position: fixed;
  background-size: 100% 85%;
  background-repeat: no-repeat;
  background-attachment: fixed;
  background-position: center;
  z-index: -1;
}

/deep/ .el-form-item__label {
  font-size: 20px;
  color: #fff;
}

/deep/ .el-step__description, el-step__title {
  font-size: 15px;
  color: #fff;
}

.el-button{
  width: 100%;
  height: 100%;
}

.el-form {
  font-size: 30px;
  margin: 6% 10%;
  width: 40%;
}

.control {
  top: 5%;
  left: 35%;
  width: 50%;
}

</style>
