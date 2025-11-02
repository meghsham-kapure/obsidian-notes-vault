1. Create required `@entity` classes. Use Lombok's `@Getter @Setter @AllArgsConstructor @NoArgsConstructor`
2. For `@Id` column use `@GernaratedValue.IDENTITY`
3. Also add fields relevant to column and column that store time stamps `@CreationTimeStamp @ModifiedTimeStamp`
4. Try to find relationship.mapping and participation along PK and FK for the database tables
5. While defining the relations you may use `@OneToOne`, `@OneToManay`,`@ManyToOne` mapping, use `@JoinColum` to define behaviours of column containing FK in Parent Table . `@JoinColumn` have properties like name, nullable, 