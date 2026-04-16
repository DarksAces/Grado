using UnityEngine;

public class CharacterControl : MonoBehaviour
{
    public float jumpForce = 22.0f;
    public float flyForce = 5.0f;
    public Transform groundCheck;
    public LayerMask groundLayer;
    public float groundCheckRadius = 0.1f;

    private Rigidbody2D rb;
    private bool isGrounded = false;
    private bool hasEnteredSecondZone = false; 

    private void Start()
    {
        rb = GetComponent<Rigidbody2D>();
        
        // FORZAMOS FÍSICAS REALES
        if (rb != null) {
            rb.bodyType = RigidbodyType2D.Dynamic;
            rb.collisionDetectionMode = CollisionDetectionMode2D.Continuous; // Más preciso
        }

        foreach (BoxCollider2D bc in GetComponents<BoxCollider2D>()) {
            bc.isTrigger = false;
        }

        SpriteRenderer sr = GetComponent<SpriteRenderer>();
        if (sr != null) sr.color = Color.cyan; 
    }

    private void Update()
    {
        isGrounded = Physics2D.OverlapCircle(groundCheck.position, groundCheckRadius, groundLayer);

        if (hasEnteredSecondZone && Input.GetMouseButton(0))
        {
            FloatInSecondZone();
        }

        if (isGrounded && Input.GetMouseButtonDown(0) && !hasEnteredSecondZone)
        {
            Jump();
        }
    }

    private void Jump()
    {
        rb.velocity = new Vector2(rb.velocity.x, jumpForce);
        rb.angularVelocity = -180f;
    }

    private void FloatInSecondZone()
    {
        if (Input.GetMouseButton(0))
        {
            rb.velocity = new Vector2(rb.velocity.x, flyForce);
        }
        else
        {
            rb.velocity = new Vector2(rb.velocity.x, -flyForce);
        }
    }

    private void FixedUpdate()
    {
        Vector3 groundCheckPosition = new Vector3(transform.position.x, transform.position.y - 0.5f, transform.position.z);
        groundCheck.position = groundCheckPosition;
    }

    private void OnTriggerEnter2D(Collider2D collision)
    {
        if (collision.CompareTag("Obstacle"))
        {
            Obstacle obs = collision.GetComponent<Obstacle>();
            if (obs != null && obs.isKillZone) {
                GameManager.Instance.PlayerHitObstacle();
            }
        }
         if (collision.CompareTag("SecondZonePortal")) // 2. bölge kapısı
        {
            hasEnteredSecondZone = true;
            GameManager.Instance.RemoveGround();
            Debug.Log("2.Bölge Giriş");

        }
    }

   
}
