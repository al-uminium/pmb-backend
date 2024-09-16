package ppm.backend.interfaces;

import com.mongodb.MongoException;
import org.bson.Document;

import java.util.List;

public interface MongoMapper<T> {
    T map(Document doc) throws MongoException;
}
