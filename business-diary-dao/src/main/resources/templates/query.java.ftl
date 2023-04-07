package ${cfg.PACKAGE_QUERY};


<#assign removePrefixEntity=entity/>
<#if cfg.REMOVE_PREFIX!?length gt 0>
    <#if entity?starts_with(cfg.REMOVE_PREFIX)>
        <#assign removePrefixEntity=entity?substring(cfg.REMOVE_PREFIX?length)/>
    </#if>
</#if>
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import com.property.proceeds.contract.req.BaseQueryReq;

/**
 * <p>
 * ${table.comment!}查询对象
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
@ApiModel(value = "${table.comment!}查询对象")
@Data
@Builder
@AllArgsConstructor
public class ${removePrefixEntity}Req  extends BaseQueryReq {


}
