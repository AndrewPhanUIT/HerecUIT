package uit.herec.service;

import java.util.Set;

import uit.herec.dao.entity.Organization;

public interface IOrganizationService {
    Organization getOrgByHyperledgerName(String hyperledgerName);
    Set<Organization> findAll();
}
