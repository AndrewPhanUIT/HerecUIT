package uit.herec.service;

import uit.herec.dao.entity.Organization;

public interface IOrganizationService {
    Organization getOrgByHyperledgerName(String hyperledgerName);
}
