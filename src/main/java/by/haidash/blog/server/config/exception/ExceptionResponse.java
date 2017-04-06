package by.haidash.blog.server.config.exception;

import com.fasterxml.jackson.annotation.JsonFormat;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * Created by haidash on 06.04.17.
 */
@Data
public class ExceptionResponse {

    private int status;

    private List<String> errors;

    private String message;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime timestamp;

    private String trace;

    private ExceptionResponse() {
    }

    public static Builder builder() {
        return new ExceptionResponse().new Builder();
    }

    public class Builder {

        private Builder() {
        }

        public Builder setStatus(final int status) {
            ExceptionResponse.this.status = status;
            return this;
        }

        public Builder setError(final String error) {

            if(ExceptionResponse.this.errors==null){
                ExceptionResponse.this.errors= new ArrayList<>();
            }

            ExceptionResponse.this.errors.add(error);
            return this;
        }

        public Builder setErrors(final List<String> errors) {

            if(ExceptionResponse.this.errors==null){
                ExceptionResponse.this.errors= new ArrayList<>();
            }

            ExceptionResponse.this.errors.addAll(errors);
            return this;
        }

        public Builder setMessage(final String message) {
            ExceptionResponse.this.message = message;
            return this;
        }

        public Builder setTrace(final String trace) {
            ExceptionResponse.this.trace = trace;
            return this;
        }

        public Builder setTimestamp(final LocalDateTime timestamp) {
            ExceptionResponse.this.timestamp = timestamp;
            return this;
        }

        public ExceptionResponse build() {
            return ExceptionResponse.this;
        }

    }
}
