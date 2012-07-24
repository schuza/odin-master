package net.floodlightcontroller.core.web.serializers;

import java.io.IOException;

import net.floodlightcontroller.odinmaster.IOdinAgent;
import net.floodlightcontroller.odinmaster.OdinClient;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;


public class OdinClientSerializer extends JsonSerializer<OdinClient> {

	
	@Override
	public void serialize(OdinClient client, JsonGenerator jgen,
			SerializerProvider provider) throws IOException,
			JsonProcessingException {
		jgen.writeStartObject();
		jgen.writeStringField("macAddress", client.getMacAddress().toString());
		String clientIpAddr = client.getIpAddress().getHostAddress();
		jgen.writeStringField("ipAddress", clientIpAddr);
		jgen.writeStringField("lvapBssid", client.getLvapBssid().toString());
		jgen.writeStringField("lvapSsid", client.getLvapSsid());
		IOdinAgent agent = client.getOdinAgent();
		if (agent != null) {
			String agentIpAddr = agent.getIpAddress().getHostAddress();
			jgen.writeStringField("agent", agentIpAddr);
		}
		else {
			jgen.writeStringField("agent", null);
		}
			
		
		jgen.writeEndObject();
	}

}