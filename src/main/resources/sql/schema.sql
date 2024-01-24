create table if not exists 'task'(
    'id'    integer primary key,
    'title' varchar(100) not null,
    'description' text not null
);