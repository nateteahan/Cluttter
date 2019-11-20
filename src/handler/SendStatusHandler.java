package handler;

import request.SendStatusRequest;
import response.SendStatusResponse;

public class SendStatusHandler {
        public SendStatusResponse sendStatusHandler(SendStatusRequest request) {
            SendStatusResponse response = new SendStatusResponse("Successful Upload");

            return response;
        }
}
