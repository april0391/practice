package edu.restful;

record Quote (String type, Value value) {
}

record Value(long id, String quote) {
}
