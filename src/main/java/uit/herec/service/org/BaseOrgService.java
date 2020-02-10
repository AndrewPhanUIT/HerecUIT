package uit.herec.service.org;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BaseOrgService {

    private Map<String, IBaseOrgService> orgServices = new HashMap<>();
    
    @Autowired
    public BaseOrgService(List<IBaseOrgService> baseOrgServices) {
        for (IBaseOrgService baseService : baseOrgServices) {
            orgServices.put(baseService.getCode(), baseService);
        }
    }
    
    public IBaseOrgService getService(String key) {
        return this.orgServices.get(key);
    }
}
