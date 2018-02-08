package com.teamrocket.naasp.service.company.doa.mongo;

import com.teamrocket.naasp.service.commons.doa.exception.FindObjectException;
import com.teamrocket.naasp.service.commons.doa.mongo.GenericMongoDoa;
import com.teamrocket.naasp.service.commons.mongo.MongoCondition;
import com.teamrocket.naasp.service.company.doa.CompanyDoa;
import com.teamrocket.naasp.service.company.model.Company;
import org.springframework.context.annotation.Conditional;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Conditional(MongoCondition.class)
public class MongoCompanyDoa extends GenericMongoDoa<Company, String> implements CompanyDoa {
    private static final String COLLECTION_NAME = "naaspCompany";
    public MongoCompanyDoa() {
        super(COLLECTION_NAME);
    }

    @Override
    public Company findCompanyByName(String companyName) {
        Query query = new Query(Criteria.where("companyData.companyName").is(companyName));
        List<Company> companies = find(query);
        return !companies.isEmpty() ? companies.get(0) : null;
    }

    private List<Company> find (Query query) {
        try {
            List<Company> companies = mongoTemplate.find(query, persistentClass, COLLECTION_NAME);
            return companies;
        } catch (Exception e) {
            throw new FindObjectException(Company.class, query.toString(), e);
        }
    }
}
