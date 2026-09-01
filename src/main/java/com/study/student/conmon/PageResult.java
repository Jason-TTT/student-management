package com.study.student.conmon;

import java.util.List;

public class PageResult<T> {
//    分页再后期扩展适用于其他的字段 所以使用泛型
        private List<T> records;
        private int pages;
        private int size;
        private long total;
        private  long totalPage;

    public PageResult(List<T> records, int pages, int size, long total, long totalPage) {
        this.records = records;
        this.pages = pages;
        this.size = size;
        this.total = total;
        this.totalPage = totalPage;
    }

    public List<T> getRecords() {
        return records;
    }

    public int getPages() {
        return pages;
    }

    public int getSize() {
        return size;
    }

    public long getTotal() {
        return total;
    }

    public long getTotalPage() {
        return totalPage;
    }
}
