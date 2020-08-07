<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item><i class="el-icon-lx-calendar"></i> 图书</el-breadcrumb-item>
                <el-breadcrumb-item>图书列表</el-breadcrumb-item>
            </el-breadcrumb>
        </div>

        <div class="container">

        <el-form :inline="true" :model="condition" class="demo-form-inline" style="margin-left: 100px">
            <el-form-item label="标题:">
                <el-input v-model="condition.condition" placeholder="请输入标题" @keyup.enter.native="selectBook()"></el-input>
            </el-form-item>

            <el-form-item>
                <el-button type="primary" @click="selectBook()" >查询</el-button>
            </el-form-item>
        </el-form>

        <el-table :data="book" border stripe fit highlight-current-row>
            <el-table-column prop="id" label="#"  align="center"></el-table-column>

            <el-table-column label="封面"  align="center">
                <template slot-scope="scope">
                    <img :src="scope.row.picture" alt="" style="height: 100px;width: 100px">
                </template>

            </el-table-column>

            <el-table-column prop="title" label="标题" align="center"></el-table-column>

            <el-table-column prop="description" label="描述"  align="center"></el-table-column>

            <el-table-column prop="url" label="地址"  align="center"></el-table-column>

            <el-table-column label="操作" align="center">
                <template slot-scope="scope">
                    <el-button
                            size="mini"
                            type="primary"
                            icon="el-icon-edit"
                            @click="getInfoBook(scope.row.id)"
                    >修改
                    </el-button>
                    <el-button
                            size="mini"
                            type="danger"
                            icon="el-icon-delete"
                            @click="removeBook(scope.row.id)"
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
                        @current-change="selectBook"
                ></el-pagination>
            </div>
        </template>
        </div>
    </div>
</template>
<script>
    import bookApi from '../../../api/admin/book';
    import linkApi from '../../../api/admin/link';

    export default {
        data(){
            return{
                condition:{
                    condition:''
                },
                book:{
                    id:'',
                    title:'',
                    description:'',
                    picture:'',
                    url:''
                },
                page: 1,
                limit: 4,
                total:''
            }
        },
        created() {
            this.selectBook();
        },
        methods:{
            //跳转链接修改link
            getInfoBook(id){
                this.$router.push({path:'/book_add',query:{bookId:id}})
            },

            removeBook(id){
                //自定义的方法
                this.$confirm("此操作将永久删除该图书, 是否继续?", "提示", {
                    confirmButtonText: "确定",
                    cancelButtonText: "取消",
                    type: "warning"
                }).then(() => {
                    bookApi.deleteBook(id).then(res=>{
                        if (res.success){
                            this.$message({
                                type: 'success',
                                message: '删除成功'
                            });
                            this.selectBook()
                        }else {
                            this.$message({
                                type: 'error',
                                message: res.message
                            });
                        }
                    })
                });
            },

            selectBook(page = 1, limit = 4){
                this.page = page; //改造让分页传page值
                this.limit = limit;
                bookApi.selectPageBook(this.page, this.limit,this.condition).then(res=>{
                    if (res.success){
                        this.book = res.queryResponseResultList.list
                        this.total = res.queryResponseResultList.total
                    }
                })
            },


        }
    }
</script>
