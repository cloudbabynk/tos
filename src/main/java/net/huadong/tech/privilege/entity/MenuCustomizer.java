/*
 * Copyright © 2015 HUADONG SOFT-TECH CO.,LTD.
 * All Rights Reserved
 */
package net.huadong.tech.privilege.entity;

import org.eclipse.persistence.config.DescriptorCustomizer;
import org.eclipse.persistence.descriptors.ClassDescriptor;
import org.eclipse.persistence.expressions.Expression;
import org.eclipse.persistence.expressions.ExpressionBuilder;
import org.eclipse.persistence.mappings.OneToManyMapping;

/**
 *
 * @author jason
 */
public class MenuCustomizer implements DescriptorCustomizer {

    @Override
    public void customize(ClassDescriptor descriptor) throws Exception {
        OneToManyMapping mapping = (OneToManyMapping) descriptor.getMappingForAttributeName("children");
        ExpressionBuilder eb = new ExpressionBuilder(mapping.getReferenceClass());
        Expression andExp = eb.get("account").equal(eb.getParameter("ACCOUNT"))
                .and(eb.get("parentId").equal(eb.getParameter("ID")));
        
        mapping.setSelectionCriteria(andExp);
    }
}
