/*
 * Copyright (C) 2015 HUADONG SOFT-TECH CO.,LTD.
 * Author: xiaojn <xiaojn@huadong.net>
 */
package net.huadong.tech.privilege.entity;

import org.eclipse.persistence.config.DescriptorCustomizer;
import org.eclipse.persistence.descriptors.ClassDescriptor;
import org.eclipse.persistence.expressions.Expression;
import org.eclipse.persistence.expressions.ExpressionBuilder;
import org.eclipse.persistence.mappings.ManyToOneMapping;

/**
 * 系统管理-用户Customizer
 *
 * @author xiaojn
 * @version 1.0.0
 * @since 2015-3-27 12:33:00
 */
public class AuthUserCustomizer implements DescriptorCustomizer {

    @Override
    public void customize(ClassDescriptor descriptor) throws Exception {
        ManyToOneMapping mapping0 = (ManyToOneMapping) descriptor.getMappingForAttributeName("tenantId");
        ExpressionBuilder eb0 = new ExpressionBuilder(mapping0.getReferenceClass());
        Expression andExp0 = eb0.get("tenantId").equal(eb0.getParameter("TENANT_ID"));
        mapping0.setSelectionCriteria(andExp0);

        ManyToOneMapping mapping1 = (ManyToOneMapping) descriptor.getMappingForAttributeName("orgnId");
        ExpressionBuilder eb1 = new ExpressionBuilder(mapping1.getReferenceClass());
        Expression andExp1 = eb1.get("orgnId").equal(eb1.getParameter("ORGN_ID"));
        mapping1.setSelectionCriteria(andExp1);
    }
}
