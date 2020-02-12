package uit.herec.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uit.herec.dao.entity.Organization;
import uit.herec.dao.repository.OrganizationRepository;
import uit.herec.service.IOrganizationService;

@Service
public class OrganizationServiceImpl implements IOrganizationService{

    @Autowired
    private OrganizationRepository orgRepository;
    
    @Override
    public Organization getOrgByHyperledgerName(String hyperledgerName) {
        return this.orgRepository.findByHyperledgerNameIgnoreCase(hyperledgerName)
                .orElse(null);
    }

}
