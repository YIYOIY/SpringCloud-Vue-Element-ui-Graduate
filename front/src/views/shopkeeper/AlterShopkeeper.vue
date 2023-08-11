<template>
  <div id="building">
    <el-form label-position="top" ref="form" :model="shopkeeper.shopkeeper" :rules="rule">
      <div style="left: 2%;width:35%;height:20%;position: relative">
        <el-form-item label="账户名称" prop="ShopkeeperName">
          <el-input v-model="shopkeeper.shopkeeper.shopkeeperName" autofocus maxlength="20" minlength="1" clearable
                    placeholder="账户名称作为登录凭证"></el-input>
        </el-form-item>

        <el-form-item label="账户密码" prop="ShopkeeperPassword">
          <el-input type="password" show-password v-model="shopkeeper.shopkeeper.shopkeeperPassword" maxlength="20" minlength="6"
                    clearable
                    placeholder="在此输入账户密码"></el-input>
        </el-form-item>

        <el-form-item label="联系方式" prop="ShopkeeperPhone">
          <el-input v-model.number="shopkeeper.shopkeeper.shopkeeperPhone" clearable maxlength="11"
                    placeholder="在此输入联系方式"></el-input>
        </el-form-item>

      </div>


      <div style="left: 30%;top:20%;width: 15%;position: absolute">
        <el-form-item label="注册日期">
          <el-date-picker clearable v-model="shopkeeper.shopkeeper.shopkeeperBirth"
                          type="date" format="YYYY 年 MM 月 DD 日" placeholder="在此选择注册日期">
          </el-date-picker>
        </el-form-item>

        <el-form-item label="企业性质">
          <el-select v-model="shopkeeper.shopkeeper.shopkeeperQuality" filterable clearable placeholder="选择企业性质" @change="next">
            <el-option label="封闭公司" value="封闭公司"/>
            <el-option label="公众公司" value="公众公司"/>
          </el-select>
        </el-form-item>

        <el-form-item label="账户余额">
          <el-tag v-model.number="shopkeeper.shopkeeper.userMoney" type="danger" >{{shopkeeper.shopkeeper.userMoney>0?shopkeeper.shopkeeper.userMoney:0}}元</el-tag>
        </el-form-item>
      </div>

      <div style="left: 12%;top:50%;width: 30%;position: absolute">
        <el-form-item label="地址" prop="ShopkeeperAddress">
          <el-input type="textarea" :autosize="{ minRows: 5, maxRows: 10 }" v-model="shopkeeper.shopkeeper.shopkeeperAddress" clearable
                    placeholder="在此输入企业地址" maxlength="1000"></el-input>
        </el-form-item>
      </div>

    </el-form>

    <div style="float:right;right: 5%;width: 40%;top:5%;position: absolute">
      <el-row justify="space-evenly" v-show="havePicture" style="top: 2%;position: absolute">
        <el-col :span="24">
          <el-image style="width: 600px; height: 500px;" :src="PICTURE" fit="contain" v-show="havePicture"/>
        </el-col>

        <el-col :span="24">
          <el-row :gutter="15" justify="space-evenly">
            <el-col :span="12">
              <el-button plain round size="large" type="primary" v-show="havePicture"><a href="picture/test/download">下载头像</a>
              </el-button>
            </el-col>
            <el-col :span="12">
              <el-button plain round size="large" type="warning" @click="havePicture = !havePicture"
                         v-show="havePicture">清空头像
              </el-button>
            </el-col>
          </el-row>
        </el-col>
      </el-row>
    </div>

    <div v-show="!havePicture" style="right:1%;bottom: 60%;width:40%;position: absolute">
      <el-upload ref="pict" class="upload-demo" :action="`picture/test/up/${store.state.adminId}`" multiple
                 :limit="1"
                 encytype="multipart/form-data" name="photo" v-show="!havePicture" :auto-upload="false"
                 :show-file-list="true" :on-success="handleBookPicture">

        <template #trigger>
          <el-button v-if="!confirmPictureButton" @click="confirmPictureButton=!confirmPictureButton" style="width: 100%" size="large" plain round type="primary">上传头像</el-button>
        </template>
        <el-button v-if="confirmPictureButton" style="width: 20%" size="large" plain round type="success" @click="handleBookPicture">确定</el-button>
        <template #tip>
          <div class="el-upload__tip" style="color: #f5bcbc;font-size: 15px">
            图片格式仅允许 jpg/png 且图片大小不超过 500kb
          </div>
        </template>

      </el-upload>
    </div>


    <div style="left: 8%;top:75%;width:50%;position: absolute">
      <el-row gutter="10">
        <el-col :span="12">
          <el-button type="warning" size="large" @click="alter()" plain round>保存修改</el-button>
        </el-col>
        <el-col :span="12">
          <el-button type="primary" size="large" @click="back()" plain round>返回</el-button>
        </el-col>
      </el-row>
    </div>

  </div>
</template>

<script setup>
import { useRouter } from "vue-router";
import {useStore} from "vuex";
import {reactive, onMounted, toRef, ref} from "vue";
import {ElButton, ElMessage, ElNotification, ElUpload} from "element-plus";
import {alterShopkeeper, getShopkeeper} from "@/api/ShopkeeperApi";
import {getPicture} from "@/api/ImgAndExcelApi";
let router = useRouter();
let store = useStore();
const prop = defineProps(["shopkeeperId"]);
let id = toRef(prop, "shopkeeperId");

// 图像上传控制按钮
let confirmPictureButton = false

// 图片显示控制
let havePicture = ref(false);
// 图片的ref对象
let pict = ref('')
let PICTURE = ref('')
let handleBookPicture = (() => {
  confirmPictureButton = false
  pict.value.submit()
  pict.value.clearFiles()
  setTimeout(() => {
    // 这里是管理员进行修改企业的信息
    getPicture(store.state.adminId).then(Response => {
      if (Response.data != null && Response.data !== "") {
        // 存入企业对象中，更新时一同传递给后端
        PICTURE.value = Response.data
        // 显示图片页面
        console.log(PICTURE.value + "数据库保存的图片地址")
        havePicture.value = true
      }
    }).catch(Error => {
      console.log(Error)
      ElNotification({
        message: Error.data.message + "  请重新输入!",
        title: '错误',
        type: 'error',
        Position: 'top-right'
      })

    })
  }, 100);
})

let rule = reactive({
  shopkeeperName: [
    {required: false, message: "请输入账户名称", trigger: "blur"},
    {min: 1, max: 20, message: "长度在 1 到 20 个字符", trigger: "blur"},
  ],
  shopkeeperPassword: [
    {required: false, message: "请输入密码", trigger: "blur"},
    {min: 6, max: 20, message: "长度在 6 到 20 个字符", trigger: "blur"},
  ],
  shopkeeperPhone: [
    {required: false, message: '请输入手机号', trigger: 'change'},
    {pattern: /^1[3|5|7|8|9]\d{9}$/, message: '请输入正确的号码格式', trigger: 'change'}
  ],
  shopkeeperAddress: [
    {required: false, message: "Please select Activity zone", trigger: "blur",},
    {min: 1, max: 1000, message: "长度在 1 到 1000 个字符", trigger: "blur"},
  ],
});


onMounted(async () => {
  await getShopkeeper(id.value).then((Response) => {
    shopkeeper.shopkeeper = Response.data;
    if (shopkeeper.shopkeeper.image !== null) {
      PICTURE.value = shopkeeper.shopkeeper.image.picture
      havePicture.value = true
    }else {
      shopkeeper.shopkeeper.image=ref({
        picture: ''
      })
    }
  });
});

// 企业对象
const shopkeeper = reactive({
  shopkeeper: {
    shopkeeperName: '',
    shopkeeperQuality: '封闭公司',
    shopkeeperPassword: '',
    shopkeeperBirth: '',
    shopkeeperPhone: '',
    shopkeeperAddress: '',
    shopkeeperMoney:'',
    image: {
      picture: '123'
    }
  }
})


// 表单对象
const form = ref(null)

let alter = (() => {
  form.value.validate(valid => {
    if (!valid) {
      ElMessage.error("请输入信息符合要求!");
      return false;
    }
    shopkeeper.shopkeeper.image.picture = PICTURE.value
    let obj = JSON.stringify(shopkeeper.shopkeeper)
    console.log("要修改的的企业对象" + obj)

    alterShopkeeper(obj).then(Response => {
      ElNotification({
        message: Response.message,
        title: '保存成功！',
        type: 'success',
        Position: 'top-left'
      });
        router.push({
          name: "adminShopkeepers",
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
    name: "adminShopkeepers",
  });
};
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
  opacity: 0.9;
  background: url(../../assets/static/star.jpg) no-repeat  center;
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

.el-form {
  font-size: 30px;
  margin: 6% 10%;
  width: 40%;
}
.el-button{
  width: 70%;
}

</style>
