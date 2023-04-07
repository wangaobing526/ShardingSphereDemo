package ${package.ServiceImpl};

<#assign removePrefixEntity=entity/>
<#if cfg.REMOVE_PREFIX!?length gt 0>
 <#if entity?starts_with(cfg.REMOVE_PREFIX)>
  <#assign removePrefixEntity=entity?substring(cfg.REMOVE_PREFIX?length)/>
 </#if>
</#if>
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.property.commons.dubbo.exception.ServiceException;
import ${package.Entity}.${entity};
import ${package.Mapper}.${table.mapperName};
import ${package.Service}.${table.serviceName};
import ${cfg.PACKAGE_BUILDER}.${removePrefixEntity}Builder;
import ${cfg.PACKAGE_MODEL}.${removePrefixEntity}Model;
import ${cfg.PACKAGE_QUERY}.${removePrefixEntity}Req;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
<#if cfg.GUANYU_AUTH_FLAG??>
import com.guanyu.common.auth.annotation.GuanyuUser;
<#else >
import com.property.auth.common.annotation.PropertyUser;
</#if>

<#if table.fieldNames?contains("updated_at") || table.fieldNames?contains("created_at")>
import java.time.LocalDateTime;
</#if>
/**
 * <p>
 * ${table.comment!} 服务实现类
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
@Service
@Slf4j
public class ${table.serviceImplName} extends ${superServiceImplClass}<${table.mapperName}, ${entity}> implements ${table.serviceName} {
    <#assign paramClassName=entity?uncap_first/>
    @Resource
    private ${removePrefixEntity}Builder ${removePrefixEntity?uncap_first}Builder;
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long saveAndUpdate(<#if cfg.GUANYU_AUTH_FLAG??>GuanyuUser<#else >PropertyUser</#if> user, ${removePrefixEntity}Model model) throws ServiceException{
        log.info("${entity}.saveAndUpdate.userId:{},userName:{}",user.getUserId(),user.getUserName());
        ${entity} ${paramClassName} = ${removePrefixEntity?uncap_first}Builder.toEntity(model);
        //公共逻辑处理:字段转换&逻辑校验等
        if(${paramClassName}.getId()!=null && ${paramClassName}.getId()>0){
            <#if table.fieldNames?contains("updated_at")>
            ${paramClassName}.setUpdatedAt(LocalDateTime.now());
            </#if>
            <#if table.fieldNames?contains("update_at")>
            ${paramClassName}.setUpdateAt(LocalDateTime.now());
            </#if>
            <#if table.fieldNames?contains("updated_by")>
            ${paramClassName}.setUpdatedBy(user.getUserName());
            </#if>
            <#if table.fieldNames?contains("update_by")>
            ${paramClassName}.setUpdateBy(user.getUserName());
            </#if>
            <#if table.fieldNames?contains("updated_name")>
            ${paramClassName}.setUpdatedName(user.getUserName());
            </#if>
            <#if table.fieldNames?contains("update_name")>
            ${paramClassName}.setUpdateName(user.getUserName());
            </#if>
            <#if table.fieldNames?contains("updated_by_id")>
            ${paramClassName}.setUpdatedById(user.getUserId());
            </#if>
            boolean success=this.updateById(${paramClassName});
            log.info("updateById success:{}",success);
        }else {
            <#if table.fieldNames?contains("created_at")>
            ${paramClassName}.setCreatedAt(LocalDateTime.now());
            </#if>
            <#if table.fieldNames?contains("created_by")>
            ${paramClassName}.setCreatedBy(user.getUserName());
            </#if>
            <#if table.fieldNames?contains("created_by_id")>
            ${paramClassName}.setCreatedById(user.getUserId());
            </#if>
            <#if table.fieldNames?contains("created_name")>
            ${paramClassName}.setCreatedName(user.getUserName());
            </#if>
            boolean success=this.save(${paramClassName});
            log.info("save success:{}",success);
        }
        //this.saveOrUpdate(slsTest);
        return ${paramClassName}.getId();
    }
    @Override
    public IPage<${removePrefixEntity}Model> searchPage(<#if cfg.GUANYU_AUTH_FLAG??>GuanyuUser<#else >PropertyUser</#if> user, ${removePrefixEntity}Req req) throws ServiceException{
        QueryWrapper<${entity}> queryWrapper = new QueryWrapper<>();
        //根据业务逻辑调整
        //queryWrapper.eq("is_delete",false);
        //queryWrapper.orderByDesc("created_at");
        IPage<${entity}> pageQuery = new Page(req.getCurrent(), req.getPageSize());
        IPage<${removePrefixEntity}Model> pageInfo=this.page(pageQuery,queryWrapper)
            .convert(entity->  ${removePrefixEntity?uncap_first}Builder.modelInit(${removePrefixEntity?uncap_first}Builder.toModel(entity)));
        return pageInfo;
    }
    @Override
    public ${removePrefixEntity}Model getById(<#if cfg.GUANYU_AUTH_FLAG??>GuanyuUser<#else >PropertyUser</#if> user, Long id) throws ServiceException{
        log.info("${entity}.getById.id:{}",id);
        ${entity} ${entity?uncap_first} = this.getById(id);
        ${removePrefixEntity}Model ${removePrefixEntity?uncap_first}Model=${removePrefixEntity?uncap_first}Builder.toModel(${entity?uncap_first});
        ${removePrefixEntity?uncap_first}Builder.modelInit(${removePrefixEntity?uncap_first}Model);
        return ${removePrefixEntity?uncap_first}Model;
    }
}

