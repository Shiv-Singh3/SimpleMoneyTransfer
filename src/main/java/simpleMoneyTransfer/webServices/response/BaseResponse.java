package simpleMoneyTransfer.webServices.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class BaseResponse {

    private Document document;

    public BaseResponse() {}

    public BaseResponse(Document document) {
        this.document = document;
    }

    @Getter
    @Setter
    public static class Document {

        private String type;

        private String namespace;

        private String id;

        private String version;

        public Document(String type, String namespace, String id, String version) {
            this.type = type;
            this.namespace = namespace;
            this.id = id;
            this.version = version;
        }
    }
}
