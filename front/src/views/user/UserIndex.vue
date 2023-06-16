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
    <br />
    <el-form :size="size" :label-position="labelPosition" ref="form" label-width="auto" :model="user.user" :rules="rule">
      <el-form-item label="姓名" prop="userName">
        <el-input v-model="user.user.userName" :model-value="user.user.userName"></el-input>
      </el-form-item>
      <el-form-item label="性别">
        <el-select v-model="user.user.userSex" filterable clearable placeholder="选择性别">
          <el-option label="男" value="男" />
          <el-option label="女" value="女" />
          <el-option label="保密" value="保密" />
        </el-select>
        <!-- <el-input v-model="user.user.userSex" :model-value="user.user.userSex"></el-input> -->
      </el-form-item>
      <el-form-item label="生日" clearable>
        <el-date-picker clearable v-model="user.user.userBirth" :model-value="user.user.userBirth" align="right"
          type="date" format="YYYY 年 MM 月 DD 日">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="密码" prop="userPassword">
        <el-input type=" password" show-password v-model="user.user.userPassword"
          :model-value="user.user.userPassword"></el-input>
      </el-form-item>
      <el-form-item label="电话">
        <el-input v-model="user.user.userPhone" :model-value="user.user.userPhone"></el-input>
      </el-form-item>
      <el-form-item label="地址" prop="userAddress">
        <el-input v-model="user.user.userAddress" :model-value="user.user.userAddress"></el-input>
      </el-form-item>

      <el-form-item label-width="80">
        <el-row justify="space-around" gutter="20">
          <el-col :span="12">
            <el-button type="success" dark @click="alter()">修改</el-button>
          </el-col>
          <el-col :span="12">
            <el-button type="danger" dark @click="back()">注销</el-button>
          </el-col>
        </el-row>
      </el-form-item>
    </el-form>
  </div>
</template>

<script setup>
import { ElMessage, ElNotification } from "element-plus";
import { useRouter } from "vue-router";
import { ref, reactive, onMounted } from "vue";
import axios from "axios";
import { useStore } from "vuex";
let router = useRouter()
let store = useStore();
const size = ref("default")
const labelPosition = ref("left")

const user = reactive({
  user: {
    userName: '',
    userSex: '',
    userPassword: '',
    userBirth: '',
    userPhone: '',
    userAddress: '',
  }
})

console.log(user.user.userName)

onMounted(async () => {
  await axios.get(`api/user?userId=${store.state.userId}`).then(Response => {
    user.user = Response.data
    console.log(user.user)
  })
})
const form = ref(null)
let alter = (() => {
  form.value.validate(valid => {
    if (!valid) {
      ElMessage.error("请输入信息符合要求!");
      return false;
    }
    let alterUser = JSON.stringify(user.user)
    axios.put('api/user', alterUser, { headers: { 'Content-Type': 'application/json' } }).then(Response => {
      let message = Response.data
      ElNotification({
        message: message,
        title: '修改成功！',
        type: 'success',
        Position: 'top-left'
      });
      router.push({
        name: 'user',
      })
    }).catch(Error => {
      ElNotification({
        message: Error.response.data + "  请重新输入!",
        title: '错误',
        type: 'error',
        Position: 'top-right'
      })
    })
  });
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

let back = (() => {
  console.log("这里是用户自己删除")
  if (confirm("确认注销?")) {
    axios.delete(`api/user?userId=${store.state.userId}`,
    ).then(Response => {
      let message = Response.data
      ElNotification({
        message: message,
        title: '成功！',
        type: 'success',
        Position: 'top-left'
      });
      sessionStorage.removeItem('user')
      store.state.userId = '';
      store.state.userName = '';
      store.state.userPassword = '';
      store.state.isUser = false;
      store.state.isUser = false
      store.state.userPassword = ''
      store.state.userPetName = ''
      store.state.userId = ''
      sessionStorage.removeItem('user')
      router.push({
        name: 'book'
      })
    }).catch(Error => {
      ElNotification({
        message: Error.response.data + "  请重新输入!",
        title: '错误',
        type: 'error',
        Position: 'top-right'
      })
    })
  }
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
