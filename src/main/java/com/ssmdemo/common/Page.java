package com.ssmdemo.common;

/**
 * 分页对象
 * Created by zhuguangchuan on 2018/6/4.
 */
public class Page {

    /**
     * 第几页
     */
    private Integer pageNo = 1;

    /**
     * 每页的条数
     */
    private Integer pageSize = 10;

    /**
     * 总条数
     */
    private Integer total;

    /**
     * 总页数
     */
    private Integer totalPages;

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
        if(this.pageSize == 0){
            this.totalPages = 0 ;
        }
        this.totalPages = this.total / this.pageSize ;
        if(this.total % this.pageSize != 0){
            this.totalPages += 1 ;
        }
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }
}
