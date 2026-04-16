using UnityEngine;
using System.Collections.Generic;

public class SecondZoneObstacleManager : MonoBehaviour
{
    public GameObject[] obstaclePrefabs;
    public Transform spawnPoint;
    public float spawnInterval = 2f;
    public float obstacleSpeed = 5f;
    public float obstacleHeightRange = 5f;
    public int maxCount = 10; // Maksimum engel sayısı

    private List<GameObject> obstacles = new List<GameObject>();

    private void Start()
    {
        InvokeRepeating("SpawnObstacle", 0f, spawnInterval);
    }

    private void SpawnObstacle()
    {
        if (obstacles.Count < maxCount)
        {
            int randomPrefabIndex = Random.Range(0, obstaclePrefabs.Length);
            float randomHeight = Random.Range(-obstacleHeightRange, obstacleHeightRange);

            Vector3 spawnPosition = spawnPoint.position + new Vector3(0f, randomHeight, 0f);
            GameObject newObstacle = Instantiate(obstaclePrefabs[randomPrefabIndex], spawnPosition, Quaternion.identity);
            obstacles.Add(newObstacle);
        }
    }

    private void Update()
    {
        MoveObstacles();
    }

    private void MoveObstacles()
    {
        for (int i = obstacles.Count - 1; i >= 0; i--)
        {
            GameObject obstacle = obstacles[i];
            if (obstacle != null)
            {
                obstacle.transform.position += Vector3.left * obstacleSpeed * Time.deltaTime;

                if (obstacle.transform.position.x < -10f)
                {
                    Destroy(obstacle);
                    obstacles.RemoveAt(i);
                }
            }
            else
            {
                obstacles.RemoveAt(i);
            }
        }
    }
}
