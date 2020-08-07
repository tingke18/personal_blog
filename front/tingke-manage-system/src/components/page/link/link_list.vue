<template>
    <div>

        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item><i class="el-icon-lx-calendar"></i> 链接</el-breadcrumb-item>
                <el-breadcrumb-item>链接列表</el-breadcrumb-item>
            </el-breadcrumb>
        </div>

        <div class="container">
        <el-form :inline="true" :model="condition" class="demo-form-inline" style="margin-left: 100px">
            <el-form-item label="标题:">
                <el-input v-model="condition.condition" placeholder="请输入标题"  @keyup.enter.native="selectLink()"></el-input>
            </el-form-item>

            <el-form-item>
                &nbsp;&nbsp;&nbsp;
                <el-button type="primary" @click="selectLink()">查询</el-button>
            </el-form-item>
        </el-form>

        <el-table :data="link" border stripe fit highlight-current-row>
           <el-table-column prop="id" label="#"  align="center"></el-table-column>

            <el-table-column label="封面"  align="center">
                <template slot-scope="scope">
                    <img :src="scope.row.avatar" alt="" style="height: 100px;width: 100px">
                </template>

            </el-table-column>

            <el-table-column prop="title" label="标题" align="center"></el-table-column>

            <el-table-column prop="category" label="分类" align="center"></el-table-column>

            <el-table-column prop="description" label="描述" width="130" align="center"></el-table-column>

             <el-table-column prop="url" label="地址" width="130" align="center"></el-table-column>

            <el-table-column label="操作" align="center">
                <template slot-scope="scope">
                    <el-button
                            size="mini"
                            type="primary"
                            icon="el-icon-edit"
                            @click="getInfoLink(scope.row.id)"
                    >修改
                    </el-button>
                    <el-button
                            size="mini"
                            type="danger"
                            icon="el-icon-delete"
                            @click="removeLink(scope.row.id)"
                    >删除
                    </el-button>
                </template>
              </el-table-column>

        </el-table>

        <template>
            <div class="block" style="margin-top:30px">
                <el-pagination
                        background
                        style="padding:30 20;text-align: center"
                        layout="total, prev, pager, next, jumper"
                        :total="total"
                        :current-page="page"
                        :page-size="limit"
                        @current-change="selectLink"
                ></el-pagination>
            </div>
        </template>
        </div>
    </div>
</template>
<script>
    import linkApi from '../../../api/admin/link';

    export default {
        data() {
            return {
                condition:{
                    condition:''
                },
                link: {
                    id: '',
                    avatar: '',
                    title: '',
                    description: '',
                    url: '',
                    categoryId: '',
                    category: ''
                },
                category: {
                    id: '',
                    category: ''
                },
                page: 1,
                limit: 8,
                total:''
            };
        },
        created() {
            this.selectLinkCategory();
            this.selectLink();
        },
        methods: {
            //跳转链接修改link
            getInfoLink(id){
                this.$router.push({path:'/link_add',query:{linkId:id}})
            },
            removeLink(id){

                //自定义的方法
                this.$confirm("此操作将永久删除该链接, 是否继续?", "提示", {
                    confirmButtonText: "确定",
                    cancelButtonText: "取消",
                    type: "warning"
                }).then(() => {
                    linkApi.deleteLink(id).then(res=>{
                        if (res.success){
                            this.$message({
                                type: 'success',
                                message: '删除成功'
                            });
                            this.selectLink()
                        }else {
                            this.$message({
                                type: 'error',
                                message: res.message
                            });
                        }
                    })
                });


            },
            selectLink(page = 1, limit = 8) {
                this.page = page; //改造让分页传page值
                this.limit = limit;

                linkApi.selectLink(this.page, this.limit,this.condition).then(res => {
                    if (res.success) {
                        this.link = res.queryResponseResultList.list;
                        this.total = res.queryResponseResultList.total

                         //遍历通过对比分类id赋值给分类名称
                          for (let i = 0;i < this.link.length; i++) {
                              for (let m = 0;m < this.category.length; m++) {
                                  if (this.link[i].categoryId == this.category[m].id) {
                                      this.link[i].category = this.category[m].category;
                                  }
                              }
                          }

                    } else {
                        console.log(res.message);
                    }
                });
            },
            selectLinkCategory() {
                linkApi.selectLinkCategory().then(res => {
                    if (res.success) {
                        this.category = res.queryResponseResultList.list;
                        console.log( this.category)
                    } else {
                        console.log(res.message);
                    }
                });
            }

        }
    };
</script>
