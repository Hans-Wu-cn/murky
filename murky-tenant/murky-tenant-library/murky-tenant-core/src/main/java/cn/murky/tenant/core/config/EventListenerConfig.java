package cn.murky.tenant.core.config;

import cn.murky.socketd.MurkyEventListener;
import org.noear.socketd.SocketD;
import org.noear.socketd.transport.client.ClientSession;
import org.noear.solon.annotation.Bean;
import org.noear.solon.annotation.Configuration;
import org.noear.solon.annotation.Inject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.StringTemplate.STR;

@Configuration
public class EventListenerConfig {


//    @Bean
    public List<ClientSession> clientSession(
            @Inject("${murky.admin.host}") String host,
            @Inject List<MurkyEventListener> murkyEventListeners) throws IOException {
        List<ClientSession> clientSessions = new ArrayList<>();
        for (MurkyEventListener listener : murkyEventListeners) {
            ClientSession clientSession = SocketD.createClient(STR."\{host}\{listener.getPath()}")
                    .listen(listener).open();
            clientSessions.add(clientSession);
        }
        return clientSessions;
    }

}
