package ${cfg.PACKAGE_CONTROLLER};


<#assign removePrefixEntity=entity/>
<#if cfg.REMOVE_PREFIX!?length gt 0>
    <#if entity?starts_with(cfg.REMOVE_PREFIX)>
        <#assign removePrefixEntity=entity?substring(cfg.REMOVE_PREFIX?length)/>
    </#if>
</#if>
<#if cfg.GUANYU_AUTH_FLAG??>
import com.guanyu.common.auth.annotation.GuanyuAuth;
import com.guanyu.common.auth.annotation.GuanyuUser;
import com.guanyu.common.web.data.JsonResult;
import com.guanyu.dubbo.exception.ServiceException;
<#else >
import com.property.auth.common.annotation.PropertyAuth;
import com.property.auth.common.annotation.PropertyUser;
import com.property.commons.dubbo.exception.ServiceException;
import com.property.commons.web.JsonResult;
</#if>
import com.property.commons.utils.json.Json;
import ${cfg.PACKAGE_MODEL}.${removePrefixEntity}Model;
import ${cfg.PACKAGE_QUERY}.${removePrefixEntity}Req;
import ${package.Service}.${table.serviceName};

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
<#if restControllerStyle>
import org.springframework.web.bind.annotation.RestController;
<#else>
import org.springframework.stereotype.Controller;
</#if>
<#if superControllerClassPackage??>
import ${superControllerClassPackage};
</#if>
import com.baomidou.mybatisplus.core.metadata.IPage;
import springfox.documentation.annotations.ApiIgnore;

/**
 * <p>
 * ${table.comment!} 前端控制器
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
@Api(value = "${table.comment!}", hidden = true,tags = "${table.comment!}")
<#if restControllerStyle>
@RestController
<#else>
@Controller
</#if>
@RequestMapping("<#if package.ModuleName??>${package.ModuleName}</#if>/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>")
<#if kotlin>
class ${table.controllerName}<#if superControllerClass??> : ${superControllerClass}()</#if>
<#else>
@Slf4j
<#if superControllerClass??>
public class ${table.controllerName} extends ${superControllerClass} {
<#else>
public class ${table.controllerName} {
</#if>
    @Resource
    private ${table.serviceName} ${table.serviceName?uncap_first};
<#if cfg.GUANYU_AUTH_FLAG??>
    @GuanyuAuth
<#else >
    @PropertyAuth
</#if>
    @ApiOperation(value = "新增&编辑")
    @PostMapping(value = "/saveAndUpdate")
    public JsonResult saveAndUpdate(@ApiIgnore <#if cfg.GUANYU_AUTH_FLAG??>GuanyuUser<#else >PropertyUser</#if> user, @RequestBody @Validated ${removePrefixEntity}Model model) throws ServiceException {
        log.info("model.json:{}", JSON.toJSONString(model));
        Long id = ${table.serviceName?uncap_first}.saveAndUpdate(user, model);
        log.info("id: {}", JSON.toJSONString(id));
        return JsonResult.ok(id);
    }
<#if cfg.GUANYU_AUTH_FLAG??>
    @GuanyuAuth
<#else >
    @PropertyAuth
</#if>
    @ApiOperation(value = "分页查询")
    @PostMapping(value = "/searchPage")
    public JsonResult<IPage<${removePrefixEntity}Model>> searchPage(@ApiIgnore <#if cfg.GUANYU_AUTH_FLAG??>GuanyuUser<#else >PropertyUser</#if> user, @RequestBody ${removePrefixEntity}Req req) throws ServiceException{
        log.info("req:{}", JSON.toJSONString(req));
        IPage<${removePrefixEntity}Model> ${removePrefixEntity?uncap_first}ModelPage = ${table.serviceName?uncap_first}.searchPage(user, req);
        log.info("total:{},recordSize:{}", ${removePrefixEntity?uncap_first}ModelPage.getTotal(),${removePrefixEntity?uncap_first}ModelPage.getRecords().size());
        return JsonResult.ok(${removePrefixEntity?uncap_first}ModelPage);
    }
<#if cfg.GUANYU_AUTH_FLAG??>
    @GuanyuAuth
<#else >
    @PropertyAuth
</#if>
    @ApiOperation(value = "根据主键id获取详情")
    @GetMapping(value = "/getById")
    public JsonResult<${removePrefixEntity}Model> getById(@ApiIgnore <#if cfg.GUANYU_AUTH_FLAG??>GuanyuUser<#else >PropertyUser</#if> user, @RequestParam Long id) throws ServiceException {
        log.info("id:{}", id);
        ${removePrefixEntity}Model ${removePrefixEntity?uncap_first}Model = ${table.serviceName?uncap_first}.getById(user, id);
        log.info("model:{}", JSON.toJSONString(${removePrefixEntity?uncap_first}Model));
        return JsonResult.ok(${removePrefixEntity?uncap_first}Model);
    }

}
</#if>
