package org.programmers.staybb.setup;

import org.programmers.staybb.domain.user.Host;
import org.programmers.staybb.repository.HostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HostSetup {

    @Autowired
    private UserSetup userSetup;

    @Autowired
    private HostRepository hostRepository;

    public Host saveHost() {
        return hostRepository.save(Host.builder().user(userSetup.saveUser()).build());
    }

    public Long getUserId() {
        return userSetup.saveUser().getId();
    }

}