package ppm.backend.mapper;

import com.mongodb.MongoException;
import org.bson.Document;
import ppm.backend.model.Ledger;

import java.util.List;

public interface MongoMapper<T> {
    T map(List<Document> doc) throws MongoException;
}
