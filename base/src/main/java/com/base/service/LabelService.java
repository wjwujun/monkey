package com.base.service;

import com.base.dao.LabelDao;
import com.base.pojo.Label;
import com.util.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class LabelService {

    @Autowired
    private LabelDao labelDao;

    @Autowired
    private IdWorker idWorker;

    public List<Label> findAll(){
        return  labelDao.findAll();
    }


    public Label findById(String id){
        return labelDao.findById(id).get();
    }

    public void save(Label label){
        label.setId(idWorker.nextId()+"");
        labelDao.save(label);
    }

    public void update(Label label){
        labelDao.save(label);
    }

    public void deleteById(String id){
        labelDao.deleteById(id);
    }


    /*条件查询*/
    public List<Label> search(Label label) {
        return labelDao.findAll(new Specification<Label>() {
            /*
            * root  根对象，就是要把条件封装到哪个对象中
            * query 查询的一些关键字，比如 group by
            * cb  用来封装 条件对象，如果直接返回null，就说不需要封装
            * */
            @Override
            public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

                //new一个数组来存所有条件
                ArrayList<Predicate> list = new ArrayList<>();

                if(!StringUtils.isEmpty(label.getLabelname())){
                    Predicate predicate = cb.like(root.get("labelname").as(String.class), "%" + label.getLabelname() + "%");//where labelname like "%aa%"
                    list.add(predicate);
                }

                if(!StringUtils.isEmpty(label.getLabelname())){
                    Predicate predicate = cb.equal(root.get("state").as(String.class),label.getState());//where state=1
                    list.add(predicate);
                }

                //new一个数组做为最终返回值条件
                Predicate[] parr = new Predicate[list.size()];
                //把list转化成数组
                parr= list.toArray(parr);

                return cb.and(parr);
            }
        });
    }

    /*分页*/
    public Page<Label> pageQuery(Label label, int page, int size) {

        //封装一个分页对象
        Pageable pageable=PageRequest.of(page-1,size);

        return  labelDao.findAll(new Specification<Label>() {
            /*
             * root  根对象，就是要把条件封装到哪个对象中
             * query 查询的一些关键字，比如 group by
             * cb  用来封装 条件对象，如果直接返回null，就说不需要封装
             * */
            @Override
            public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

                //new一个数组来存所有条件
                ArrayList<Predicate> list = new ArrayList<>();

                if(!StringUtils.isEmpty(label.getLabelname())){
                    Predicate predicate = cb.like(root.get("labelname").as(String.class), "%" + label.getLabelname() + "%");//where labelname like "%aa%"
                    list.add(predicate);
                }

                if(!StringUtils.isEmpty(label.getLabelname())){
                    Predicate predicate = cb.equal(root.get("state").as(String.class),label.getState());//where state=1
                    list.add(predicate);
                }

                //new一个数组做为最终返回值条件
                Predicate[] parr = new Predicate[list.size()];
                //把list转化成数组
                parr= list.toArray(parr);

                return cb.and(parr);
            }
        },pageable);
    }
}
