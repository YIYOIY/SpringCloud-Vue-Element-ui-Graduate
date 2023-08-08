<template>
  <div id="building">
    <el-form label-position="top" ref="form" :model="admin.admin" :rules="rule">

      <div style="left: 2%;width:35%;height:20%;position: relative">
        <el-form-item label="账户名称" prop="adminName">
          <el-input v-model="admin.admin.adminName" autofocus @change="next" maxlength="20" minlength="1" clearable
                    placeholder="账户名称作为登录凭证"></el-input>
        </el-form-item>

        <el-form-item label="账户密码" prop="adminPassword">
          <el-input type="password" show-password v-model="admin.admin.adminPassword" maxlength="20" minlength="6"
                    clearable @change="next"
                    placeholder="在此输入账户密码"></el-input>
        </el-form-item>

        <el-form-item label="联系方式" prop="adminPhone">
          <el-input v-model.number="admin.admin.adminPhone" clearable maxlength="11" @change="next"
                    placeholder="在此输入联系方式"></el-input>
        </el-form-item>
      </div>


      <div style="left: 30%;top:20%;width: 15%;position: absolute">
        <el-form-item label="出生日期">
          <el-date-picker clearable v-model="admin.admin.adminBirth"
                          type="date" format="YYYY 年 MM 月 DD 日" @change="next" placeholder="在此选择出生日期">
          </el-date-picker>
        </el-form-item>

        <el-form-item label="性别">
          <el-select v-model="admin.admin.adminSex" filterable clearable placeholder="选择性别" @change="next">
            <el-option label="男" value="男"/>
            <el-option label="女" value="女"/>
            <el-option label="保密" value="保密"/>
          </el-select>
        </el-form-item>


      </div>
      <div style="left: 12%;top:50%;width: 30%;position: absolute">
        <el-form-item label="地址" prop="adminAddress" @change="next">
          <el-input type="textarea" :autosize="{ minRows: 5, maxRows: 10 }" v-model="admin.admin.adminAddress" clearable
                    placeholder="在此输入收货地址" maxlength="1000"></el-input>
        </el-form-item>
      </div>
    </el-form>

    <div style="right: 2%;width: 50%;top:5%;position: absolute">
      <el-row>

        <el-row  v-show="havePicture">
          <el-col :span="24">
            <el-image style="width: 780px; height: 580px;margin-top: 5%;z-index: 2" :src="admin.admin.image.picture" fit="contain" v-show="havePicture"/>
          </el-col>

          <el-col :span="24">
            <el-row style="float:right;width: 65%">
              <el-col :span="8">
                <el-button plain round size="large" type="primary" v-show="havePicture"><a href="picture/test/download">下载头像</a></el-button>
              </el-col>
              <el-col :span="8">
                <el-button plain round size="large" type="warning"  @click="havePicture = !havePicture" v-show="havePicture">清空头像</el-button>
              </el-col>
            </el-row>
          </el-col>

        </el-row>

        <el-row justify="space-around" v-show="!havePicture" style="margin-top: 10%">
          <el-upload ref="pict" class="upload-demo" :action="`picture/test/up/${id}`" multiple :limit="1"
                     encytype="multipart/form-data" name="photo" v-show="!havePicture" :auto-upload="false"
                     :show-file-list="true" :on-success="handleBookPicture"
          >
            <template #trigger>
              <el-col :span="2">
                <el-button dark round type="primary">上传头像，限制一张</el-button>
              </el-col>
            </template>
            <el-col :span="12">
              <el-button plain round type="success" @click="handleBookPicture">点击上传服务器</el-button>
            </el-col>
            <template #tip>
              <div class="el-upload__tip" STYLE="float:right;right:5%;color: #efe8e8;font-size: 20px">
                jpg/png files with a size less than 500kb
              </div>
            </template>
          </el-upload>
        </el-row>
      </el-row>
    </div>

    <div style="left: 10%;top:74%;width:50%;position: absolute">
      <el-row justify="space-between" :gutter="5">
        <el-col :span="12">
          <el-button style="width: 50%;font-size: 20px;color: #f5f1f1" type="primary" size="large" @click="add()" round>注册</el-button>
        </el-col>
        <el-col :span="12">
          <el-button style="width: 50%;font-size: 20px;color: #151515" size="large" @click="cancelAdd()" round>取消</el-button>
        </el-col>
      </el-row>
    </div>

    <div style="left: 5%;width:50%;bottom:10px;position: absolute">
      <el-steps :active="active" align-center finish-status="success"
                style="width: 100%;background-color: rgba(243,243,243,0.2);border-radius: 10px">
        <el-step title="Step 1" description="填写姓名"/>
        <el-step title="Step 2" description="输入密码"/>
        <el-step title="Step 3" description="根据需要填写其他信息"/>
        <el-step title="Step 4" description="注册！"/>
      </el-steps>
    </div>
  </div>
</template>

<script setup>
import {ref, reactive} from "vue";
import {useRouter} from "vue-router";
import {useRoute} from "vue-router";
import {ElButton, ElMessage, ElNotification, ElUpload} from "element-plus";
import {addAdmin} from "@/api/AdminApi";
import {getPicture} from "@/api/ImgAndExcelApi";

let router = useRouter()
let route = useRoute()

let isEnroll = ref(false)
if (route.query.enroll) {
  isEnroll.value = true
}
console.log("是否从首页进入而非用户管理页进入" + isEnroll.value)

let active = ref(0)
let next = (() => {
  active.value++
})

let id = Math.ceil(Math.random() * 1000000)
console.log("未注册用户保存头像的随机id" + id)
let havePicture = ref(false);

let pict = ref('')
let handleBookPicture = (() => {
  pict.value.submit()
  pict.value.clearFiles()
  setTimeout(() => {
    getPicture(id).then(Response => {
      console.log(Response.data + "后端返回的值")
      if (Response.data != null && Response.data !== "") {
        //对后端返回值验证后进行赋值
        admin.admin.image.picture = Response.data
        // 显示图片页面
        console.log(admin.admin.image.picture + "数据库保存的图片地址")
        havePicture.value = true
      }
    }).catch(Error => {
      ElNotification.error(Error.data.message)
      console.log(Error)
    })
  }, 100);
})


let rule = reactive({
  adminName: [
    {required: true, message: "请输入账户名称", trigger: "blur"},
    {min: 1, max: 20, message: "长度在 1 到 20 个字符", trigger: "blur"},
  ],
  adminPassword: [
    {required: true, message: "请输入密码", trigger: "blur"},
    {min: 6, max: 20, message: "长度在 6 到 20 个字符", trigger: "blur"},
  ],
  adminPhone: [
    {required: false, message: '请输入手机号', trigger: 'change'},
    {pattern: /^1[3|5|7|8|9]\d{9}$/, message: '请输入正确的号码格式', trigger: 'change'}
  ],
  adminAddress: [
    {required: false, message: "Please select Activity zone", trigger: "blur",},
    {min: 1, max: 1000, message: "长度在 1 到 100 个字符", trigger: "blur"},
  ]
});

const admin = reactive({
  admin: {
    adminName: '',
    adminSex: '保密',
    adminPassword: '',
    adminBirth: '',
    adminPhone: '',
    adminAddress: '',
    image: {
      picture: ''
    }
  }
})

const form = ref(null)
const add = (() => {
  form.value.validate(valid => {
    if (!valid) {
      ElMessage.error("请输入信息符合要求!");
      return false;
    }

    let Admin = JSON.stringify(admin.admin)
    console.log("要保存的用户对象信息" + Admin)

    addAdmin(Admin).then(Response => {
      if (isEnroll.value) {
        ElNotification({
          message: Response.message,
          title: '录入成功！',
          type: 'success',
          Position: 'top-left'
        });
        router.push('/admins')
      }
    }).catch(Error => {
      ElNotification({
        message: Error.data.message + " 换一个吧!",
        title: '错误',
        type: 'error',
        Position: 'top-right'
      })
    })
  });
})

const cancelAdd = (() => {
    router.push('/admins')
})

</script>

<style scoped>
#building {
  bottom: 0;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  font-size: large;
  position: fixed;
  background-size: 100% 100%;
  background: url(../../assets/static/enroll.jpg) no-repeat fixed center;
  z-index: -1;
}

/deep/ .el-form-item__label {
  font-size: 20px;
  color: #fff;
}

/deep/ .el-step__description {
  font-size: 15px;
  color: #fff;
}

.el-form {
  font-size: 30px;
  margin: 6% 10%;
  width: 40%;
}

.el-input {
  --el-input-border-radius: 18px;
}

/deep/ .el-input__inner {
  border-radius: 18px;
  width: 100%;
}
</style>