
    package exceptions;

    public class ApiException extends RuntimeException {
        private final int statusCode;

        public ApiException(int statusCode, String msg){
            super(msg); //see explanation below
            this.statusCode = statusCode;
        }

        public int getStatusCode() {
            return statusCode;
        }
    }

