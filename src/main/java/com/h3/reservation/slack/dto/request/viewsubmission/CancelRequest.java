package com.h3.reservation.slack.dto.request.viewsubmission;

/**
 * @author heebg
 * @version 1.0
 * @date 2019-12-18
 */
public class CancelRequest {
    class CancelView {
        private String privateMetadata;
        private String callbackId;

        public CancelView() {
        }

        public CancelView(String privateMetadata, String callbackId) {
            this.privateMetadata = privateMetadata;
            this.callbackId = callbackId;
        }

        public String getPrivateMetadata() {
            return privateMetadata;
        }

        public String getCallbackId() {
            return callbackId;
        }
    }

    private String type;
    private CancelView view;

    public CancelRequest() {
    }

    public CancelRequest(String type, CancelView view) {
        this.type = type;
        this.view = view;
    }

    public String getType() {
        return type;
    }

    public CancelView getView() {
        return view;
    }

    public String getCallbackId() {
        return view.getCallbackId();
    }

    public String getPrivateMetadata() {
        return view.getPrivateMetadata();
    }
}
