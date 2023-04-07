package ${package.Service};

import ${package.Entity}.${entity};
import ${superServiceClassPackage};
<#assign removePrefixEntity=entity/>
<#if cfg.REMOVE_PREFIX!?length gt 0>
    <#if entity?starts_with(cfg.REMOVE_PREFIX)>
        <#assign removePrefixEntity=entity?substring(cfg.REMOVE_PREFIX?length)/>
    </#if>
</#if>
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.property.commons.dubbo.exception.ServiceException;
import ${cfg.PACKAGE_MODEL}.${removePrefixEntity}Model;
import ${cfg.PACKAGE_QUERY}.${removePrefixEntity}Req;
<#if cfg.GUANYU_AUTH_FLAG??>
import com.guanyu.common.auth.annotation.GuanyuUser;
<#else >
import com.property.auth.common.annotation.PropertyUser;
</#if>
/**
 * <p>
 * ${table.comment!} 服务类
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
<#if kotlin>
interface ${table.serviceName} : ${superServiceClass}<${entity}>
<#else>
public interface ${table.serviceName} extends ${superServiceClass}<${entity}> {
    /**
    * description功能描述:
    *       ${table.comment!}-新增和编辑
    * @param user 当前登录用户
    * @param model ${table.comment!}model
    * @return:
    * @Author:${author}
    * @Date: ${.now}
    */
    Long saveAndUpdate(<#if cfg.GUANYU_AUTH_FLAG??>GuanyuUser<#else >PropertyUser</#if> user, ${removePrefixEntity}Model model) throws ServiceException;

    /**
    * description功能描述:
    *       ${table.comment!}-分页查询
    * @param user 当前登录用户
    * @param model ${table.comment!}model
    * @return: 分页对象
    * @Author:${author}
    * @Date: ${.now}
    */
    IPage<${removePrefixEntity}Model> searchPage(<#if cfg.GUANYU_AUTH_FLAG??>GuanyuUser<#else >PropertyUser</#if> user, ${removePrefixEntity}Req req) throws ServiceException;

    /**
    * description功能描述:
    *       ${table.comment!}-根据主键查询
    * @param user 当前登录用户
    * @param id,主键id
    * @return: 返回model对象
    * @Author:${author}
    * @Date: ${.now}
    */
    ${removePrefixEntity}Model getById(<#if cfg.GUANYU_AUTH_FLAG??>GuanyuUser<#else >PropertyUser</#if> user, Long id) throws ServiceException;

}
</#if>
